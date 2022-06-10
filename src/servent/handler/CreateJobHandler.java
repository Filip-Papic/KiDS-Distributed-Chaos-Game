package servent.handler;

import app.AppConfig;
import app.Job.Job;
import app.Job.JobChaos;
import app.Job.NewJobCreator;
import servent.message.CreateJobMessage;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.StartMessage;
import servent.message.util.MessageUtil;

public class CreateJobHandler implements MessageHandler {

    private Message clientMessage;

    public CreateJobHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if(clientMessage.getMessageType() == MessageType.CREATE) {
            if (clientMessage.getSenderPort() != AppConfig.myServentInfo.getListenerPort()) {
                CreateJobMessage createJobMessage = (CreateJobMessage) clientMessage;

                NewJobCreator newJobCreator = new NewJobCreator();
                Job job = newJobCreator.createJob();

                StartMessage startMessage = new StartMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                        AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(), AppConfig.myServentInfo.getListenerPort(),
                        job.getJobName(), null);    //!!!!!!!!!!!!!!!!!!!!!!!                            //!!!!!!!!!!!!
                MessageUtil.sendMessage(startMessage);
            }
        }

    }
}
