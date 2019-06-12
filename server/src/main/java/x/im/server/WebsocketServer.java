package x.im.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;
import org.apache.tomcat.util.log.SystemLogHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


@Component
public class WebsocketServer {

    public WebsocketServer() {
    }

    @Autowired
    MsgHandler msgHandler;

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-codec", new HttpServerCodec());
                            ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
                            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            ch.pipeline().addLast(new WebSocketHandle());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            System.out.println("websocket server bind");
            ChannelFuture future = sb.bind(9001);
//            future.channel().closeFuture().sync();
            System.out.println("websocket sever finish");
        } catch (Exception e) {
            e.printStackTrace();
//        } finally {
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
        }
    }


    class WebSocketHandle extends SimpleChannelInboundHandler<Object> {

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
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("get new msg");
            if (msg instanceof FullHttpRequest) {
                handleHttpRequest(ctx, (FullHttpRequest) msg);
                return;
            }
            if (msg instanceof CloseWebSocketFrame) {
                System.out.println("close websocket");
                return;
            }
            if (msg instanceof PingWebSocketFrame) {
                System.out.println("ping websocket ");
                return;
            }
            if (msg instanceof TextWebSocketFrame) {
                TextWebSocketFrame twf = (TextWebSocketFrame) msg;
                String txt = twf.text();
                msgHandler.readMsg(ctx, txt);
//                System.out.println(txt);
//                ctx.channel().writeAndFlush(new TextWebSocketFrame("get the msg:" + txt));
            }

        }

        private WebSocketServerHandshaker handshaker;

        private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
            if (!req.decoderResult().isSuccess()
                    || (!"websocket".equals(req.headers().get("Upgrade")))) {
                sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                        HttpResponseStatus.BAD_REQUEST));
                return;
            }
            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null, false);
            handshaker = wsFactory.newHandshaker(req);
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                handshaker.handshake(ctx.channel(), req);
            }
        }
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx,
                                         FullHttpRequest req, FullHttpResponse res) {
        // 返回应答给客户端
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(),
                    CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpUtil.setContentLength(res, res.content().readableBytes());
        }

        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

}
