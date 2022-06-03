package servent.message;

public class PutMessage extends BasicMessage {

	private static final long serialVersionUID = 5163039209888734276L;

	public PutMessage(int senderPort, int receiverPort, String senderIP, String recieverIP, int key, int value) {
		super(MessageType.PUT, senderPort, receiverPort, senderIP, recieverIP,key + ":" + value);
	}
}
