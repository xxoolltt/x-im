X-IM服务器
------
使用netty做socket连接器。同时使用Spring框架来处理业务逻辑，数据库链接等。

当前是单机版本。后续会开发分布式版本。

####单机版本功能设计
-[x] socket连接
-[x] websocket连接
-[x] 用户登录
-[x] 消息推送
-[x] 离线消息
-[x] 群消息
-[x] 心跳消息
-[ ] 聊天室功能
-[ ] protobuf封装消息
-[ ] 多客户端消息同步

####开发注意事项
- 修改数据库相关信息。如果连接不上数据，程序会运行失败。
```
spring.datasource.url=jdbc:mysql://172.16.64.162:3306/imdb?useSSL=false
spring.datasource.username=****
spring.datasource.password=*****
```
- 如需要client进行调试。请自行修改对应端口。

- 欢迎大家来issue讨论.




