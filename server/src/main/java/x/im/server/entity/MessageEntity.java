package x.im.server.entity;

import com.alibaba.fastjson.JSONObject;
import x.im.common.entity.Message;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class MessageEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "msg_id")
    protected Long id = null;
    @Column(name = "msg_type")
    protected long type;
    @Column(name = "msg_from")
    protected Long from;
    @Column(name = "msg_to")
    protected Long to;
    @Column(name = "msg_time")
    protected Long msgTime;
    @Column(name = "msg_content")
    protected String content;
    @Column(name = "msg_ext")
    protected String ext;
    @Column(name = "msg_received")
    protected long received;

    @Column(name = "send_mid")
    protected Long sendId;
    @Column(name = "conversation_type")
    protected int conversationType;

    @OneToOne
    @JoinColumn(name = "msg_id")
    private MessageStatus messageStatus;


    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public Long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Long msgTime) {
        this.msgTime = msgTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public long getReceived() {
        return received;
    }

    public void setReceived(long received) {
        this.received = received;
    }

    public Long getSendId() {
        return sendId;
    }

    public void setSendId(Long sendId) {
        this.sendId = sendId;
    }

    public int getConversationType() {
        return conversationType;
    }

    public void setConversationType(int conversationType) {
        this.conversationType = conversationType;
    }

    public void parseFromJSON(JSONObject jsonObject) {
        setMsgTime(System.currentTimeMillis());
        setType(jsonObject.getIntValue("msg_type"));
        setTo(jsonObject.getLongValue("to"));
        setContent(jsonObject.getString("content"));
        setExt(jsonObject.getString("ext"));
        setSendId(jsonObject.getLongValue("sendId"));
        setConversationType(jsonObject.getIntValue("conversation_type"));
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", msgTime=" + msgTime +
                ", content='" + content + '\'' +
                ", ext='" + ext + '\'' +
                '}';
    }
}
