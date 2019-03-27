package x.im.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    private static final Map<Integer, Channel> userMap = new ConcurrentHashMap<>(16);

    public static void addUser(int userId, ChannelHandlerContext cxt) {
        userMap.put(userId, cxt.channel());
    }

    public static void remove(int userId) {
        userMap.remove(userId);
    }

    public static Channel find(int user_id) {
        return userMap.get(user_id);
    }

}
