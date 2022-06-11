package servent.handler;

import app.AppConfig;
import app.Job.Job;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.NewJobAddedMessage;
import servent.message.StartMessage;
import servent.message.util.MessageUtil;

public class NewJobAddedHandler implements MessageHandler {

    private Message clientMessage;

    public NewJobAddedHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if (clientMessage.getMessageType() == MessageType.NEW_JOB_ADDED) {
            NewJobAddedMessage newJobAddedMessage = (NewJobAddedMessage) clientMessage;

            if(newJobAddedMessage.getOriginalSenderPort() != AppConfig.myServentInfo.getListenerPort()) { //&&
            //if (clientMessage.getSenderPort() != AppConfig.myServentInfo.getListenerPort()) {

                Job job = newJobAddedMessage.getJobFractal();

                AppConfig.jobNamesMap.put(job.getJobName(), job);

                System.out.println(AppConfig.jobNamesMap.keySet());

                NewJobAddedMessage newJobAddedMessage1 = new NewJobAddedMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                        AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(), newJobAddedMessage.getOriginalSenderPort(),
                        job.getJobName(), job);
                MessageUtil.sendMessage(newJobAddedMessage1);
            }
        } else {
            AppConfig.timestampedErrorPrint("New job handler got a message that is not NEW_JOB_ADDED");
        }
    }
}
