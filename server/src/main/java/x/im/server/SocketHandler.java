package x.im.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import x.im.common.Const;
import x.im.server.controller.User;
import x.im.server.entity.MessageEntity;
import x.im.common.entity.CSMsg;
import x.im.server.entity.UserEntity;
import x.im.server.service.MessageService;
import x.im.server.service.UserService;


@Component
@ChannelHandler.Sharable
public class SocketHandler extends SimpleChannelInboundHandler {

    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;
    @Autowired
    MsgHandler msgHandler;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        msgHandler.removeChannel(ctx);
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            System.out.println("idle checked ");
            ctx.channel().close();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        msgHandler.readMsg(ctx, msg.toString());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        msgHandler.removeChannel(ctx);
    }

}
