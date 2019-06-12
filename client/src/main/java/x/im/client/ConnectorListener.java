package x.im.client;

import x.im.common.entity.CSMsg;

public interface ConnectorListener {
    void onConnected();

    void onDisConnected();

    void onMsgReceived(CSMsg msg);
}
