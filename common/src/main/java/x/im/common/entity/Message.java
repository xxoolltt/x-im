package x.im.common.entity;

import com.alibaba.fastjson.JSONObject;

public class Message {

    protected Long id = null;
    protected long type;
    protected Long from;
    protected Long to;
    protected Long msgTime;
    protected String content;
    protected String ext;
    protected long received;
    protected Long sendId;

    private int conversationType;


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

    public void setSendId(long sendId) {
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

    public int getConversationType() {
        return conversationType;
    }

    public void setConversationType(int conversationType) {
        this.conversationType = conversationType;
    }

    public  JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("type", type);
        jsonObject.put("from", from);
        jsonObject.put("to", to);
        jsonObject.put("msgTime", msgTime);
        jsonObject.put("content", content);
        jsonObject.put("ext", ext);
        jsonObject.put("sendId", sendId);
        jsonObject.put("conversationType", conversationType);
        return jsonObject;
    }

    public void parseFromJSON(JSONObject jsonObject) {
        setMsgTime(System.currentTimeMillis());
        setType(jsonObject.getIntValue("msg_type"));
        setTo(jsonObject.getIntValue("to"));
        setContent(jsonObject.getString("content"));
        setExt(jsonObject.getString("ext"));
        setSendId(jsonObject.getLongValue("sendId"));
        setConversationType(jsonObject.getIntValue("conversation_type"));
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
