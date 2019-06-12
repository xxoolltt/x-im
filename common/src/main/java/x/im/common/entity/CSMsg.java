package x.im.common.entity;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class CSMsg {
    int action;
    String msg;
    JSONObject body;

    public CSMsg() {
    }

    public CSMsg(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public JSONObject getBody() {
        return body;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }

    public String toJSON() {
        return JSON.toJSONString(this);
    }

}
