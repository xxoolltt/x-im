package x.im.server.entity;


public class HttpResponse<T> {
    public int code;
    public String msg;
    public T data;

    public HttpResponse(T data) {
        this.data = data;
        this.code = 0;
    }

    public HttpResponse() {
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static HttpResponse success(Object data) {
        HttpResponse msg = new HttpResponse();
        msg.setCode(0);
        msg.setData(data);
        msg.setMsg("请求成功");
        return msg;
    }

    public static HttpResponse error(int code, String errorMsg) {
        HttpResponse msg = new HttpResponse();
        msg.setCode(code);
        msg.setMsg(errorMsg);
        return msg;
    }

    public static HttpResponse error(String errorMsg) {
        HttpResponse msg = new HttpResponse();
        msg.setCode(1000);
        msg.setMsg(errorMsg);
        return msg;
    }
}
