package x.im.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    private static final Map<Long, Channel> userMap = new ConcurrentHashMap<>(16);

    public static void addUser(long userId, ChannelHandlerContext cxt) {
        userMap.put(userId, cxt.channel());
    }

    public static void remove(long userId) {
        userMap.remove(userId);
    }

    public static Channel find(long user_id) {
        return userMap.get(user_id);
    }

}
