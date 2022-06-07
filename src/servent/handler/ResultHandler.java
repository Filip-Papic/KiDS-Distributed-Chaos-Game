package servent.handler;

import app.AppConfig;
import app.Job.Job;
import app.Job.JobChaos;
import app.Job.JobImage;
import app.Job.JobResult;
import servent.message.*;
import servent.message.util.MessageUtil;

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
                JobImage jobImage = new JobImage();
                //jobImage.imageResult(AppConfig.jobNameResultsMap.get(resultMessage.getJobName()));
                jobImage.imageResult(AppConfig.jobNamesMap.get(resultMessage.getJobName()));
            }
        } else {
            AppConfig.timestampedErrorPrint("Welcome handler got a message that is not RESULT");
        }
    }
}
