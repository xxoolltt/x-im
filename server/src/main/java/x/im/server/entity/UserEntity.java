package x.im.server.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @Column(name = "user_id")
    private long userId;
    @Column(name = "create_time")
    private long createTime;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "head_img")
    private String headImg;
    @Column(name = "status")
    private long status;
    @Column(name = "pwd")
    private String pwd;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }


    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }


    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
