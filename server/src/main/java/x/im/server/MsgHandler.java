package x.im.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import x.im.common.Const;
import x.im.common.entity.CSMsg;
import x.im.server.entity.MessageEntity;
import x.im.server.entity.UserEntity;
import x.im.server.service.MessageService;
import x.im.server.service.UserService;

import java.util.List;

@Service
public class MsgHandler {

    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;


    AttributeKey<Long> channel_user_name = AttributeKey.newInstance("CHANNEL_USER_NAME");

    public void findHistory(ChannelHandlerContext ctx) {
        messageService.findHistory(getUserFromChannel(ctx.channel()), ctx.channel());
    }


    public void readMsg(ChannelHandlerContext ctx, String msg) {
        CSMsg csMsg = JSON.parseObject(msg, CSMsg.class);
        int action = csMsg.getAction();

        //心跳
        if (action == Const.ACTION_PING) {
            ctx.channel().writeAndFlush((new CSMsg(Const.ACTION_PONG)).toJSON());
            return;
        }
        //检测用户登录
        if (action != Const.ACTION_LOGIN && checkUserValid(ctx))
            return;

        switch (action) {
            case Const.ACTION_LOGIN:
                login(csMsg.getBody(), ctx);
                break;
            case Const.ACTION_SEND:
                sendMsg(csMsg.getBody(), ctx);
                break;
            case Const.ACTION_RECEIVE:
                msgReceived(csMsg.getBody(), ctx);
                break;
            case Const.ACTION_FIND_UNRECEIVED:
                findHistory(ctx);
                break;
        }
    }

    public void removeChannel(ChannelHandlerContext ctx) {
        long userId = getUserFromChannel(ctx.channel());
        if (userId <= 0)
            return;
        UserManager.remove(userId);
    }

    private void login(JSONObject jsonObject, ChannelHandlerContext ctx) {
        System.out.println("login " + jsonObject);
        long userId = jsonObject.getLongValue("user_id");
        String pwd = jsonObject.getString("pwd");
        if (validUser(userId, pwd)) {
            System.out.println("login success");
            response(ctx, Const.CODE_LOGIN_SUCCESS, "");
            addUser(ctx, userId);
        } else {
            System.out.println("login failed");
            response(ctx, Const.CODE_ERROR_LOGIN_FAILED, "login failed");
        }
    }

    private void addUser(ChannelHandlerContext ctx, long userId) {
        Attribute<Long> attribute = ctx.channel().attr(channel_user_name);
        Long oldUserId = attribute.getAndSet(userId);
        if (oldUserId != null && oldUserId != 0) {
            UserManager.remove(oldUserId);
        }
        UserManager.addUser(userId, ctx);
    }

    private long getUserFromChannel(Channel ch) {
        Attribute<Long> attribute = ch.attr(channel_user_name);
        if (attribute == null || attribute.get() == null)
            return 0;
        return attribute.get();
    }


    private void sendMsg(JSONObject jsonObject, ChannelHandlerContext ctx) {
        if (!jsonObject.containsKey("to") || !jsonObject.containsKey("sendId")) {
            response(ctx, Const.CODE_ERROR_FORMAT, "format error");
            return;
        }
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.parseFromJSON(jsonObject);
        messageEntity.setFrom(getUserFromChannel(ctx.channel()));
        if (messageEntity.getConversationType() == Const.CONVERSATION_TYPE_GROUP)
            messageService.insertGroupMessage(messageEntity, ctx.channel());
        if (messageEntity.getType() == Const.MSG_TYPE_CMD)
            MessageService.sendMsgWithResponse(messageEntity, ctx.channel());
        else
            messageService.insertMessage(messageEntity, ctx.channel());
    }

    public void response(ChannelHandlerContext ctx, int i, String s) {
        CSMsg response = new CSMsg(i);
        response.setMsg(s);
        ctx.writeAndFlush(response.toJSON());
    }

    private void msgReceived(JSONObject jsonObject, ChannelHandlerContext ctx) {
        if (jsonObject !=null &&jsonObject.containsKey("msg_id")) {
            messageService.messageReceived(jsonObject.getLongValue("msg_id"));
            return;
        }
        if (jsonObject !=null &&jsonObject.containsKey("msg_ids")) {
            JSONArray jsonArray = jsonObject.getJSONArray("msg_ids");
            messageService.messageReceived(jsonArray.toJavaList(Long.class));
            return;
        }

        response(ctx, Const.CODE_ERROR_FORMAT, "format error");
    }

    private boolean checkUserValid(ChannelHandlerContext ctx) {
        long userId = getUserFromChannel(ctx.channel());
        if (userId <= 0) {
            response(ctx, Const.CODE_ERROR_USER_INVALID, "invalid user");
            return true;
        }
        return false;
    }

    private boolean validUser(long userId, String pwd) {
        UserEntity user = userService.getUser(userId);
        return user != null && user.getPwd().equals(pwd);
    }
}
