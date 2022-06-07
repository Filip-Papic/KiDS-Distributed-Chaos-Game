package servent.handler;

import app.AppConfig;
import app.Job.JobImage;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.ResultFractalMessage;
import servent.message.ResultMessage;
import servent.message.util.MessageUtil;

public class ResultFractalHandler implements MessageHandler {

    private Message clientMessage;

    public ResultFractalHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if (clientMessage.getMessageType() == MessageType.FRACTAL_RESULT) {
            ResultFractalMessage resultFractalMessage = (ResultFractalMessage) clientMessage;

            if(resultFractalMessage.getOriginalSenderPort() != AppConfig.myServentInfo.getListenerPort()) { //&&
                //resultFractalMessage.getSenderIP() != AppConfig.myServentInfo.getIpAddress()) {

                ResultFractalMessage resultFractalMessage1 = new ResultFractalMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.chordState.getNextNodePort(),
                        AppConfig.myServentInfo.getIpAddress(), AppConfig.chordState.getNextNodeIP(),
                        resultFractalMessage.getOriginalSenderPort(), resultFractalMessage.getJobName(), resultFractalMessage.getFractalID());
                MessageUtil.sendMessage(resultFractalMessage1);

            } else {
                JobImage jobImage = new JobImage();
                //jobImage.imageResult(AppConfig.jobNameResultsMap.get(resultMessage.getJobName()));
                jobImage.imageFractalIDResult(AppConfig.jobNamesMap.get(resultFractalMessage.getJobName()),
                                    resultFractalMessage.getFractalID());
            }
        } else {
            AppConfig.timestampedErrorPrint("Result handler got a message that is not FRACTAL_RESULT");
        }
    }
}
