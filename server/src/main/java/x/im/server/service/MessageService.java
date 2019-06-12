package x.im.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import x.im.common.Const;
import x.im.common.entity.CSMsg;
import x.im.server.UserManager;
import x.im.server.dao.MessageRepository;
import x.im.server.dao.MessageStatusRepository;
import x.im.server.entity.GroupMember;
import x.im.server.entity.MessageEntity;
import x.im.server.entity.MessageStatus;

import java.util.ArrayList;
import java.util.List;

import static x.im.common.Const.ACTION_SEND;
import static x.im.common.Const.ACTION_SEND_LIST;
import static x.im.common.Const.CODE_SEND_SUCCESS;


@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageStatusRepository messageStatusRepository;

    @Autowired
    GroupService groupService;

    @Async
    public void insertMessage(MessageEntity msg, Channel ch) {
        messageRepository.save(msg);
        messageStatusRepository.save(new MessageStatus(msg));
        sendMsgWithResponse(msg, ch);
    }

    @Async
    public void insertGroupMessage(MessageEntity msg, Channel ch) {
        List<GroupMember> memberList = groupService.getGroupMember(msg.getTo());
        if (memberList == null || memberList.size() <= 0)
            return;
        messageRepository.save(msg);
        responseSuccess(msg, ch);
        List<MessageStatus> statusList = new ArrayList<>();
        for (int i = 0; i < memberList.size(); i++) {
            GroupMember groupMember = memberList.get(i);
            MessageStatus e = new MessageStatus();
            e.setFrom(msg.getFrom());
            e.setTo(groupMember.getUserId());
            e.setMsgId(msg.getId());
            statusList.add(e);
        }
        messageStatusRepository.saveAll(statusList);
        for (int i = 0; i < memberList.size(); i++) {
            long to = memberList.get(i).getUserId();
            if (to == msg.getFrom())
                continue;
            sendMsg(msg, to);
        }
    }

    @Async
    public void findHistory(long user_id, Channel ch) {
        List<MessageEntity> messageEntities = messageRepository.getAllByToAndMessageStatusReceivedOrderByMsgTime(user_id, 0);
        CSMsg csMsg = new CSMsg(ACTION_SEND_LIST);
        JSONArray jsonObject = (JSONArray) JSON.toJSON(messageEntities);
        JSONObject object = new JSONObject();
        object.put("list", jsonObject);
        csMsg.setBody(object);
        _send(ch, csMsg);
    }

    public static void sendMsgWithResponse(MessageEntity msg, Channel ch) {
        responseSuccess(msg, ch);
        sendMsg(msg);
    }

    private static void responseSuccess(MessageEntity msg, Channel ch) {
        CSMsg csMsg = new CSMsg(CODE_SEND_SUCCESS);
        JSONObject response = new JSONObject();
        response.put("send_mid", msg.getSendId());
        response.put("msg_id", msg.getId());
        response.put("time", msg.getMsgTime());
        csMsg.setBody(response);
        _send(ch, csMsg);
    }

    private static void _send(Channel ch, CSMsg csMsg) {
        if (ch != null && ch.isActive()) {
            ch.writeAndFlush(csMsg.toJSON());
        }
    }

    public static void sendMsg(MessageEntity msg, long to) {
        Channel ch = UserManager.find(to);
        if (ch == null || !ch.isActive()) {
            System.out.println("not find ch");
        } else {
            CSMsg csMsg = new CSMsg(Const.ACTION_SEND);
            csMsg.setBody(((JSONObject) JSON.toJSON(msg)));
            _send(ch, csMsg);

        }
    }

    public static void sendMsg(MessageEntity msg) {
        sendMsg(msg, msg.getTo());
    }

    @Async
    public void messageReceived(List<Long> msgIds) {
        List<MessageStatus> list = messageStatusRepository.getAllByMsgIdIn(msgIds);
        if (list == null || list.isEmpty())
            return;
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setReceived(1);
        }
        messageStatusRepository.saveAll(list);
    }

    @Async
    public void messageReceived(long msgId) {
        MessageStatus msg = messageStatusRepository.findByMsgId(msgId);
        System.out.println("message recieved :" + msg);
        if (msg == null || msg.getReceived() == 1)
            return;
        System.out.println("message recieved :" + msg);
        msg.setReceived(1);
        messageStatusRepository.save(msg);
    }

}
