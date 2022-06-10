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

                System.out.println("START NODES: " + AppConfig.chordState.getAllNodeInfo());

                Job job = AppConfig.jobNamesMap.get(startMessage.getJobName());
                int id = AppConfig.myServentInfo.getId();
                Map<Integer, List<Point>> map = startMessage.getIdJobMap();

                if(map.containsKey(AppConfig.myServentInfo.getId())) {
                    System.out.println("IMA MOG POSLA");
                    List<Point> newPoints = map.get(id);

                    String newName = job.getJobName()+"_"+id;

                    Job jobFractal = new Job(newName, job.getJobPointNumber(), job.getJobDistance(),
                                                job.getJobWidth(), job.getJobHeight(), newPoints);
                    AppConfig.jobNamesMap.put(jobFractal.getJobName(), jobFractal);

                    System.out.println(AppConfig.jobNamesMap.keySet());

                    NewJobAddedMessage newJobAddedMessage = new NewJobAddedMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                            AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(), startMessage.getOriginalSenderPort(),
                            jobFractal);
                    MessageUtil.sendMessage(newJobAddedMessage);

                    JobChaos.startJob(jobFractal);
                } else {
                    System.out.println("NEMA MOG POSLA");
                }

                StartMessage startMessageNew = new StartMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                        AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(), startMessage.getOriginalSenderPort(),
                        startMessage.getJobName(), startMessage.getIdJobMap());
                MessageUtil.sendMessage(startMessageNew);
            }
        } else {
            AppConfig.timestampedErrorPrint("Start handler got a message that is not Start");
        }
    }
}
