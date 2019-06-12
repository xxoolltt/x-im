package x.im.client;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import x.im.common.Const;
import x.im.common.entity.CSMsg;

public class ClientMsgHandler extends SimpleChannelInboundHandler {

    ChannelHandlerContext ctx;

    ConnectorListener connectorListener;

    public ClientMsgHandler(ConnectorListener connectorListener) {
        this.connectorListener = connectorListener;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            System.out.print("send ping");
            sendCSMsg(new CSMsg(Const.ACTION_PING));
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("channel active");
        this.ctx = ctx;
        if (connectorListener != null)
            connectorListener.onConnected();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("channel inactive");
        if (connectorListener != null)
            connectorListener.onDisConnected();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("read " + msg);
        CSMsg csMsg = JSON.parseObject(msg.toString(), CSMsg.class);
        if (connectorListener != null)
            connectorListener.onMsgReceived(csMsg);
    }

    public boolean isActive() {
        return !(ctx == null || !ctx.channel().isActive());
    }

    public void sendCSMsg(CSMsg msg) {
        if (!isActive()) {
            return;
        }
        ctx.channel().writeAndFlush(JSON.toJSONString(msg));
    }
}
