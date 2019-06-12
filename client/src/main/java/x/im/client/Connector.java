package x.im.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import x.im.common.entity.CSMsg;

import java.util.concurrent.TimeUnit;

public class Connector {

    EventLoopGroup group = new NioEventLoopGroup();

    Bootstrap bs = new Bootstrap();

    public Connector(ConnectorListener connectorListener) {
        this.connectorListener = connectorListener;
//        try {

        bs.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        clientMsgHandler = new ClientMsgHandler(connectorListener);
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IdleStateHandler(0,5,0));
                        pipeline.addLast(new JsonObjectDecoder());
                        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(clientMsgHandler);
                    }
                });
//            cf.channel().closeFuture().sync();
//        } catch (Exception e) {
//            e.printStackTrace();
//            disConnect();
//        } finally {
//            group.shutdownGracefully();
//        }
    }

    public ClientMsgHandler getClientMsgHandler() {
        return clientMsgHandler;
    }

    ConnectorListener connectorListener;
    ChannelFuture cf;
    Channel channel;

    ClientMsgHandler clientMsgHandler;

    public void connect() {
        if (channel != null && channel.isActive())
            return;
        ChannelFuture cf = bs.connect("127.0.0.1", 9000);
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("connect");
                    channel = future.channel();
                } else {
                    future.channel().eventLoop().schedule(() -> {
                        connect();
                    }, 10, TimeUnit.SECONDS);
                }
            }
        });
    }

    public void sendCSMsg(CSMsg csMsg) {
        clientMsgHandler.sendCSMsg(csMsg);
    }

    public void disConnect() {
        if (cf != null)
            cf.channel().closeFuture();
    }

}
