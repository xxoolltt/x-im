package x.im.server.entity;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "msg_id")
    private Long id = null;
    @Column(name = "msg_type")
    private long type;
    @Column(name = "msg_from")
    private Long from;
    @Column(name = "msg_to")
    private Long to;
    @Column(name = "msg_time")
    private Long msgTime;
    @Column(name = "msg_content")
    private String content;
    @Column(name = "msg_ext")
    private String ext;
    @Column(name = "msg_received")
    private long received;

    @Column(name = "send_mid")
    private Long sendId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getSendId() {
        return sendId;
    }

    public void setSendId(Long sendId) {
        this.sendId = sendId;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getReceived() {
        return received;
    }

    public void setReceived(long received) {
        this.received = received;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }


    public long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(long msgTime) {
        this.msgTime = msgTime;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", msgTime=" + msgTime +
                ", content='" + content + '\'' +
                ", ext='" + ext + '\'' +
                '}';
    }
}
