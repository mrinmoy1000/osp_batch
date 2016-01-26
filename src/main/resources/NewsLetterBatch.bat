echo ON
echo Executing Newsletter Batch.

%JAVA_HOME%/bin/java -jar osp-batch.jar com.flamingos.tech.osp.batch.LaunchJobNewsLetter

echo Newsletter Batch Executed.
echo OFF