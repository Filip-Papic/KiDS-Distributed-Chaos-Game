package servent.message;

public class TellGetMessage extends BasicMessage {

	private static final long serialVersionUID = -6213394344524749872L;

	public TellGetMessage(int senderPort, int receiverPort, String senderIP, String recieverIP, int key, int value) {
		super(MessageType.TELL_GET, senderPort, receiverPort, senderIP, recieverIP, key + ":" + value);
	}
}
