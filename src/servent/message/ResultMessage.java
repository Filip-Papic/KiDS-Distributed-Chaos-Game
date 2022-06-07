package servent.message;

public class ResultMessage extends BasicMessage {

    private String jobName;
    public ResultMessage(int senderPort, int receiverPort, String senderIP, String recieverIP, String messageText) {
        super(MessageType.RESULT, senderPort, receiverPort, senderIP, recieverIP);
        this.jobName = messageText;
    }

    public String getJobName() {
        return jobName;
    }
}
