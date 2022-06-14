package servent.handler;

import app.AppConfig;
import app.Job.Job;
import app.Job.JobChaos;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.NewJobAddedMessage;
import servent.message.StartMessage;
import servent.message.util.MessageUtil;

import java.awt.*;
import java.util.*;
import java.util.List;

public class StartHandler implements MessageHandler {

    private Message clientMessage;

    public StartHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if(clientMessage.getMessageType() == MessageType.START) {
            StartMessage startMessage = (StartMessage) clientMessage;

            //if (clientMessage.getSenderPort() != AppConfig.myServentInfo.getListenerPort()) {
            if(startMessage.getOriginalSenderPort() != AppConfig.myServentInfo.getListenerPort()) { //&&
                //clientMessage.getSenderIP() != AppConfig.myServentInfo.getIpAddress()) {

                startYourJob(startMessage);
                AppConfig.myServentInfo.setIdle(true);

                StartMessage startMessageNew = new StartMessage(AppConfig.myServentInfo.getListenerPort(),
                        AppConfig.chordState.getNextNodePort(),//AppConfig.chordState.getNextNodeForKey(),
                        AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(), startMessage.getOriginalSenderPort(),
                        startMessage.getJobName(), startMessage.getIdJobMap());
                MessageUtil.sendMessage(startMessageNew);
            } else {
                startYourJob(startMessage);
            }
        } else {
            AppConfig.timestampedErrorPrint("Start handler got a message that is not Start");
        }
    }

    private void startYourJob(StartMessage startMessage){
        Job job = AppConfig.jobNamesMap.get(startMessage.getJobName());
        int id = AppConfig.myServentInfo.getId();//getChordId();
        Map<Integer, List<Point>> map = startMessage.getIdJobMap();

        if(map.containsKey(id)) {
            System.out.println("IMA MOG POSLA");
            AppConfig.myServentInfo.setIdle(false);
            List<Point> newPoints = map.get(id);

            String newName = job.getJobName()+"_"+id;

            Job jobFractal = new Job(newName, job.getJobPointNumber(), job.getJobDistance(),
                    job.getJobWidth(), job.getJobHeight(), newPoints);
            AppConfig.jobNamesMap.put(jobFractal.getJobName(), jobFractal);

            System.out.println(AppConfig.jobNamesMap.keySet());

            Job job1 = JobChaos.startJob(jobFractal);

            NewJobAddedMessage newJobAddedMessage = new NewJobAddedMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                    AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(), AppConfig.myServentInfo.getListenerPort(),
                    job1.getJobName(), job1);
            MessageUtil.sendMessage(newJobAddedMessage);

        } else {
            AppConfig.myServentInfo.setIdle(true);
            System.out.println("NEMA MOG POSLA");
        }
    }

}
