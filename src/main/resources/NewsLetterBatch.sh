echo "Executing Newsletter Batch..."

$JAVA_HOME/bin/java -jar osp-batch.jar com.flamingos.tech.osp.batch.LaunchJobNewsLetter

echo "Newsletter Batch Executed..."

# Cron expression to exeute NewsLetterBatch.sh file everyday 1:00 A.M.
#1 00 * * * sh /home/system2/workspace/osp_batch/target/OSP_BATCH_DIST/NewsLetterBatch.sh 
