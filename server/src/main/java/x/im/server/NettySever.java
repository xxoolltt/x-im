package x.im.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettySever {

    public NettySever() {
        System.out.println("netty server instance");
    }

    @Autowired
    SocketHandler socketHandler;

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
                            ch.pipeline().addLast(new IdleStateHandler(10, 0, 0));
                            ch.pipeline().addLast(new JsonObjectDecoder());
                            ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(socketHandler);
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = sb.bind(9000);
//            future.channel().closeFuture().sync();
            System.out.println("bing socket");
        } catch (Exception e) {
            e.printStackTrace();
//        } finally {
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
        }
    }


}
