package x.im.server.entity;


import javax.persistence.*;

@Entity
@Table(name = "message_status")
public class MessageStatus {

    @Column(name = "msg_from")
    private Long from;
    @Column(name = "msg_to")
    private Long to;
    @Id
    @Column(name = "msg_id")
    private Long msgId;
    @Column(name = "msg_received")
    private long received;
    @Column(name = "msg_acked")
    private long acked;
    @Column(name = "ack_received")
    private long ackReceived;



    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }


    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }


    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }


    public long getReceived() {
        return received;
    }

    public void setReceived(long received) {
        this.received = received;
    }


    public long getAcked() {
        return acked;
    }

    public void setAcked(long acked) {
        this.acked = acked;
    }


    public long getAckReceived() {
        return ackReceived;
    }

    public void setAckReceived(long ackReceived) {
        this.ackReceived = ackReceived;
    }

    public MessageStatus() {
    }

    public MessageStatus(MessageEntity messageEntity) {
        this.msgId = messageEntity.getId();
        this.from = messageEntity.getFrom();
        this.to = messageEntity.getTo();
    }
}
