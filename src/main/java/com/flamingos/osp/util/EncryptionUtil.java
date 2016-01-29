package com.flamingos.osp.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.log4j.Logger;

public class EncryptionUtil {

  private static final Logger LOGGER = Logger.getLogger(EncryptionUtil.class);
  /** Fortify scan report fix start **/
  private static final int iteration = 10000;
  private static final int salt_length = 8;
  private static final String salt_algorithm = "SHA1PRNG";
  private static final int key_length = 256;
  private static final String encryption_function = "PBKDF2WithHmacSHA1";


  /** Fortify scan report fix end **/
  /* Encryption */
  public static synchronized String encryption(String plainText) throws NoSuchAlgorithmException,
      InvalidKeySpecException {
    LOGGER.info("Entering into Encryption");
    SecureRandom sr = SecureRandom.getInstance(salt_algorithm);
    byte[] salt = new byte[salt_length];
    sr.nextBytes(salt);

    char[] regPasswordArr = plainText.toCharArray();
    PBEKeySpec spec = new PBEKeySpec(regPasswordArr, salt, iteration, key_length);
    SecretKeyFactory skf = SecretKeyFactory.getInstance(encryption_function);

    byte[] hash = skf.generateSecret(spec).getEncoded();
    byte[] concatByte = new byte[salt.length + hash.length];
    System.arraycopy(salt, 0, concatByte, 0, salt.length);
    System.arraycopy(hash, 0, concatByte, salt.length, hash.length);

    String encryptedValue = new String(Base64.encodeBase64(concatByte));
    return encryptedValue;
  }

  /* Decryption */
  public static synchronized boolean decryption(String plaintext, String encryptedValue)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] passFromDb = Base64.decodeBase64(encryptedValue.getBytes());
    byte[] encryptionSalt = new byte[salt_length];
    System.arraycopy(passFromDb, 0, encryptionSalt, 0, salt_length);

    char[] loginPasswordArr = plaintext.toCharArray();
    PBEKeySpec repeatSpec = new PBEKeySpec(loginPasswordArr, encryptionSalt, iteration, key_length);
    SecretKeyFactory skf = SecretKeyFactory.getInstance(encryption_function);

    byte[] reHash = skf.generateSecret(repeatSpec).getEncoded();
    byte[] checkByte = new byte[encryptionSalt.length + reHash.length];
    System.arraycopy(encryptionSalt, 0, checkByte, 0, encryptionSalt.length);
    System.arraycopy(reHash, 0, checkByte, encryptionSalt.length, reHash.length);

    String loginPassString = new String(Base64.encodeBase64(checkByte));
    boolean status = (encryptedValue.equals(loginPassString)) ? true : false;
    return status;
  }
}
