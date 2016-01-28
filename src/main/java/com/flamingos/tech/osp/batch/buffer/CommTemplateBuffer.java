/**
 * 
 */
package com.flamingos.tech.osp.batch.buffer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.flamingos.tech.osp.batch.model.JobStatusModel;
import com.flamingos.tech.osp.batch.newsletter.model.CommJobTemplate;

/**
 * @author Mrinmoy
 *
 */

public class CommTemplateBuffer {
  /**
   * Key is professional sub category (for templates valid for all professional sub-category i.e.
   * sub-category of professionals.)
   */
  private static Map<Integer, List<CommJobTemplate>> templateForProfessionals =
      new HashMap<Integer, List<CommJobTemplate>>();
  /**
   * Key is ALL (for templates valid for all client.)
   */
  private static Map<String, List<CommJobTemplate>> templateForClients =
      new HashMap<String, List<CommJobTemplate>>();

  /**
   * Key is <job_id>_<template_id>.
   */
  private static Map<String, CommJobTemplate> jobTemplateIdMap =
      new HashMap<String, CommJobTemplate>();

  private static Set<Integer> professionalJobStatusSet = new HashSet<Integer>();

  private static Set<Integer> clientJobStatusSet = new HashSet<Integer>();

  private static Map<Integer, JobStatusModel> jobStatusMap = new HashMap<Integer, JobStatusModel>();

  private static Set<Integer> professionalJobSubCatSet = new HashSet<Integer>();

  {
    professionalJobStatusSet.add(0);
    clientJobStatusSet.add(0);
    professionalJobSubCatSet.add(0);
  }

  /**
   * @return the templateForProfessionals
   */
  public static Map<Integer, List<CommJobTemplate>> getTemplateForProfessionals() {
    return templateForProfessionals;
  }

  /**
   * @return the templateForClients
   */
  public static Map<String, List<CommJobTemplate>> getTemplateForClients() {
    return templateForClients;
  }

  /**
   * @return the jobTemplateIdMap
   */
  public static Map<String, CommJobTemplate> getJobTemplateIdMap() {
    return jobTemplateIdMap;
  }

  /**
   * @return the professionalJobStatusSet
   */
  public static Set<Integer> getProfessionalJobStatusSet() {
    return professionalJobStatusSet;
  }

  /**
   * @return the clientJobStatusSet
   */
  public static Set<Integer> getClientJobStatusSet() {
    return clientJobStatusSet;
  }

  /**
   * @return the professionalJobSubCatSet
   */
  public static Set<Integer> getProfessionalJobSubCatSet() {
    return professionalJobSubCatSet;
  }

  /**
   * @return the jobStatusMap
   */
  public static Map<Integer, JobStatusModel> getJobStatusMap() {
    return jobStatusMap;
  }
}
