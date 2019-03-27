package x.im.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import x.im.server.entity.Message;
import x.im.server.entity.Reponse;
import x.im.server.service.MessageService;


@Component
@ChannelHandler.Sharable
public class MsgHandler extends SimpleChannelInboundHandler {

    @Autowired
    MessageService messageService;

    AttributeKey<Integer> channel_user_name = AttributeKey.newInstance("CHANNEL_USER_NAME");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        JSONObject jsonObject = new JSONObject(msg.toString());
        if (!jsonObject.has("action"))
            return;
        switch (jsonObject.optInt("action")) {
            case Const.ACTION_LOGIN:
                login(jsonObject.optJSONObject("para"), ctx);
                break;
            case Const.ACTION_SEND:
                sendMsg(jsonObject, ctx);
                break;
            case Const.ACTION_RECEIVE:
                msgReceived(jsonObject, ctx);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }

    private void login(JSONObject jsonObject, ChannelHandlerContext ctx) {
        System.out.println("login " + jsonObject);
        int userId = jsonObject.optInt("user_id");
        String token = jsonObject.optString("token");
        if (validUser(userId, token)) {
            System.out.println("login success");
            response(ctx, Const.CODE_SUCCESS, "");
            addUser(ctx, userId);
        } else {
            System.out.println("login failed");
            response(ctx, Const.CODE_ERROR_LOGIN_FAILED, "login failed");
        }
    }

    private void addUser(ChannelHandlerContext ctx, int userId) {
        Attribute<Integer> attribute = ctx.channel().attr(channel_user_name);
        Integer oldUserId = attribute.getAndSet(userId);
        if (oldUserId != null && oldUserId != 0) {
            UserManager.remove(oldUserId);
        }
        UserManager.addUser(userId, ctx);
    }

    private int getUserFromChannel(Channel ch) {
        Attribute<Integer> attribute = ch.attr(channel_user_name);
        if (attribute == null)
            return 0;
        return attribute.get();
    }


    private void sendMsg(JSONObject jsonObject, ChannelHandlerContext ctx) {
        if (!jsonObject.has("to") || !jsonObject.has("content")
                || !jsonObject.has("send_mid")
        ) {
            response(ctx, Const.CODE_ERROR_FORMAT, "format error");
            return;
        }
        int userId = getUserFromChannel(ctx.channel());
        if (userId <= 0) {
            response(ctx, Const.CODE_ERROR_USER_INVALID, "invalid user");
            return;
        }

        Message message = new Message();
        message.setMsgTime(System.currentTimeMillis());
        message.setFrom(userId);
        message.setType(0);
        message.setTo(jsonObject.optInt("to"));
        message.setContent(jsonObject.optString("content"));
        message.setExt(jsonObject.optString("ext"));
        message.setSendId(jsonObject.optLong("send_mid"));
        messageService.insertMessage(message, ctx.channel());
    }

    public void response(ChannelHandlerContext ctx, int i, String s) {
        Reponse response = new Reponse();
        response.setCode(i);
        response.setMsg(s);
        ctx.writeAndFlush(response.toJSON().toString());
    }

    private void msgReceived(JSONObject jsonObject, ChannelHandlerContext ctx) {
        int userId = getUserFromChannel(ctx.channel());
        if (userId <= 0) {
            response(ctx, Const.CODE_ERROR_USER_INVALID, "invalid user");
            return;
        }
        if (!jsonObject.has("msg_id")) {
            response(ctx, Const.CODE_ERROR_FORMAT, "format error");
            return;
        }
        messageService.messageReceived(jsonObject.optLong("msg_id"));
    }

    private boolean validUser(int userId, String token) {
        if (userId <= 0)
            return false;
        return "123456".equals(token);
    }
}
