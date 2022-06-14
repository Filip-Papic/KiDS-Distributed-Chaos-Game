package servent.message;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class StatusMessage extends BasicMessage {

    private String jobName;
    private int fractalID;
    private int originalSenderPort;
    private Map<Integer, Integer> status;

    public StatusMessage(int senderPort, int receiverPort, String senderIP, String recieverIP, int originalSenderPort, String jobName,
                         int fractalID, Map<Integer, Integer> status) {
        super(MessageType.STATUS, senderPort, receiverPort, senderIP, recieverIP);
        this.originalSenderPort = originalSenderPort;
        this.jobName = jobName;
        this.fractalID = fractalID;
        this.status = status;
    }

    public String getJobName() {
        return jobName;
    }

    public int getFractalID() {
        return fractalID;
    }

    public int getOriginalSenderPort() {
        return originalSenderPort;
    }

    public Map<Integer, Integer> getStatus() {
        return status;
    }

    public void addStatus(int id, List<Point> statusPoints) {
        this.status.put(id, statusPoints.size());
    }
}
