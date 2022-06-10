package servent.handler;

import app.AppConfig;
import app.Job.Job;
import app.Job.JobChaos;
import app.Job.JobImage;
import app.Job.JobResult;
import servent.message.*;
import servent.message.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class ResultHandler implements MessageHandler{

    private Message clientMessage;

    public ResultHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if (clientMessage.getMessageType() == MessageType.RESULT) {
            ResultMessage resultMessage = (ResultMessage) clientMessage;

            if(resultMessage.getOriginalSenderPort() != AppConfig.myServentInfo.getListenerPort()) { //&&
                //clientMessage.getSenderIP() != AppConfig.myServentInfo.getIpAddress()) {

                //if(resultMessage.getFractalID() == -1) {
                //dodaj svoj deo posla i salji dalje ako nisi ti trazio result

                ResultMessage resultMessage1 = new ResultMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                        AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(),
                        resultMessage.getOriginalSenderPort(), resultMessage.getJobName());
                MessageUtil.sendMessage(resultMessage1);

            } else {
                //jobImage.imageResult(AppConfig.jobNameResultsMap.get(resultMessage.getJobName()));

                if(resultMessage.getJobName().contains(" ")) {
                    System.out.println("RESULT MESSAGE: " + resultMessage.getJobName());

                    JobImage jobImage = new JobImage();
                    List<Point> jobResults = new ArrayList<>();
                    String[] nameFractal = resultMessage.getJobName().split(" ");
                    String jobName = nameFractal[0];
                    int fractalID = Integer.parseInt(nameFractal[1]);

                    String newJobName = jobName + "_" + fractalID;

                    for(String jbName : AppConfig.jobNamesMap.keySet()){
                        if(jbName.equals(newJobName)){
                            System.out.println(AppConfig.jobNamesMap.keySet());
                            jobResults.addAll(AppConfig.jobNamesMap.get(jbName).getJobResults());
                        }
                    }
                    Job job = AppConfig.jobNamesMap.get(newJobName);
                    Job jobNew = new Job(job.getJobName(), job.getJobPointNumber(), job.getJobDistance(), job.getJobWidth(),
                            job.getJobHeight(), job.getJobCoordinates());
                    jobNew.setJobResults(jobResults);

                    jobImage.imageResult(jobNew);
                } else {
                    System.out.println("RESULT MESSAGE: " + resultMessage.getJobName());

                    JobImage jobImage = new JobImage();
                    List<Point> jobResults = new ArrayList<>();
                    String jobName = resultMessage.getJobName();

                    for(String jbName : AppConfig.jobNamesMap.keySet()){
                        if(jbName.startsWith(jobName)){
                            jobResults.addAll(AppConfig.jobNamesMap.get(jbName).getJobResults());
                        }
                    }
                    Job job = AppConfig.jobNamesMap.get(jobName);
                    Job jobNew = new Job(job.getJobName(), job.getJobPointNumber(), job.getJobDistance(), job.getJobWidth(),
                            job.getJobHeight(), job.getJobCoordinates());
                    jobNew.setJobResults(jobResults);

                    jobImage.imageResult(jobNew);
                }


            }
        } else {
            AppConfig.timestampedErrorPrint("Welcome handler got a message that is not RESULT");
        }
    }
}
