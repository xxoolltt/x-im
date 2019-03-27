package x.im.server.entity;

import org.springframework.boot.configurationprocessor.json.JSONObject;

public class Reponse {
    int code;
    String msg;
    String ext;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", code);
            jsonObject.put("msg", msg);
            jsonObject.put("ext", ext);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
