package servent.message;

import app.Job.Job;

public class CreateJobMessage extends BasicMessage {
    public CreateJobMessage(int senderPort, int receiverPort, String senderIP, String recieverIP) {
        super(MessageType.CREATE, senderPort, receiverPort, senderIP, recieverIP);
    }
}
