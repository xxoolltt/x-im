package x.im.common;

public class Const {
    public static final int ACTION_LOGIN = 1;
    public static final int ACTION_SEND = 2;
    public static final int ACTION_RECEIVE = 3;
    public static final int ACTION_FIND_UNRECEIVED = 4;
    public static final int ACTION_SEND_LIST = 5;
    public static final int ACTION_PING = 100;
    public static final int ACTION_PONG = 101;

    public static final int MSG_TYPE_NORMAL = 1;
    public static final int MSG_TYPE_CMD = 2;

    public static final int CONVERSATION_TYPE_SINGLE = 1;
    public static final int CONVERSATION_TYPE_GROUP = 2;
    public static final int CONVERSATION_TYPE_CHATROOM = 3;

    public static final int CODE_LOGIN_SUCCESS = 1001;
    public static final int CODE_SEND_SUCCESS = 1002;
    public static final int CODE_ERROR_FORMAT = 2011;
    public static final int CODE_ERROR_LOGIN_FAILED = 2010;
    public static final int CODE_ERROR_USER_INVALID = 2012;

}
