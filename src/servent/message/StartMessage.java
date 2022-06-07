package servent.message;

public class StartMessage extends BasicMessage{
    private String jobName;

    public StartMessage(int senderPort, int receiverPort, String senderIP, String recieverIP, String messageText) {
        super(MessageType.START, senderPort, receiverPort, senderIP, recieverIP, messageText);

        this.jobName = messageText;
    }

    public String getJobName() {
        return jobName;
    }
}
