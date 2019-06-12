package x.im.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import x.im.server.entity.HttpResponse;
import x.im.server.entity.UserEntity;
import x.im.server.service.UserService;

@RestController
public class User {


    @Autowired
    UserService service;

    @RequestMapping(value = "/user/login")
    public HttpResponse<UserEntity> login(
            @RequestParam(value = "user_id", required = true)
                    Long userId,
            @RequestParam(value = "pwd", required = true)
                    String pwd) {

        UserEntity userEntity = service.getUser(userId);
        if (userEntity == null)
            return HttpResponse.error("无效账号");

        if (!userEntity.getPwd().equals(pwd))
            return HttpResponse.error("密码错误");

        return new HttpResponse<>(userEntity);

    }


}
