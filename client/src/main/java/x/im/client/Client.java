package x.im.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import x.im.common.Const;
import x.im.common.entity.CSMsg;
import x.im.common.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class Client {

    public Client() {
        connect();
    }

    ConnectorListener connectorListener = new ConnectorListener() {
        @Override
        public void onConnected() {
            System.out.println("connected ");
//            login();
        }

        @Override
        public void onDisConnected() {
            System.out.println("disconnected ");
        }

        @Override
        public void onMsgReceived(CSMsg msg) {
            System.out.print("action :" + msg.getAction() + "  body:" + msg.getBody());
            switch (msg.getAction()) {
                case Const.CODE_ERROR_FORMAT:
                    break;
                case Const.CODE_LOGIN_SUCCESS:
                    getHistoryMsg();
                    break;
                case Const.ACTION_SEND:
                    if (msg.getBody() != null && msg.getBody().containsKey("id"))
                        msgReceived(msg.getBody().getLongValue("id"));
                    break;
                case Const.ACTION_SEND_LIST:
                    if (msg.getBody() == null || !msg.getBody().containsKey("list"))
                        return;
                    JSONArray msgList = msg.getBody().getJSONArray("list");
                    if (msgList == null || msgList.size() <= 0)
                        return;
                    ArrayList<Long> ids = new ArrayList<>();
                    for (int i = 0; i < msgList.size(); i++) {
                        JSONObject o = (JSONObject) msgList.get(i);
                        ids.add(o.getLongValue("id"));
                    }
                    msgReceived(ids);
                    break;
            }
        }
    };
    Connector connector = new Connector(connectorListener);


    public void connect() {
        connector.connect();
    }

    public void login(Long user_id, String pwd) {
        CSMsg csMsg = new CSMsg(Const.ACTION_LOGIN);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", user_id);
        jsonObject.put("pwd", pwd);
        csMsg.setBody(jsonObject);
        connector.sendCSMsg(csMsg);
    }

    public void sendMsg(Message message) {
        message.setSendId(System.currentTimeMillis());
        CSMsg csMsg = new CSMsg(Const.ACTION_SEND);
        csMsg.setBody(message.toJSON());
        connector.sendCSMsg(csMsg);
    }

    private void getHistoryMsg() {
        CSMsg csMsg = new CSMsg(Const.ACTION_FIND_UNRECEIVED);
        connector.sendCSMsg(csMsg);
    }

    private void msgReceived(List<Long> msgIDs) {
        CSMsg csMsg = new CSMsg(Const.ACTION_RECEIVE);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg_ids", JSON.toJSON(msgIDs));
        csMsg.setBody(jsonObject);
        connector.sendCSMsg(csMsg);

    }

    private void msgReceived(long msgID) {
        CSMsg csMsg = new CSMsg(Const.ACTION_RECEIVE);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg_id", msgID);
        csMsg.setBody(jsonObject);
        connector.sendCSMsg(csMsg);
    }

}
