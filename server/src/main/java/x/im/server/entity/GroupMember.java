package x.im.server.entity;

import javax.persistence.*;

@Entity
@Table(name = "group_member")
public class GroupMember {

    @Id
    @Column(name = "group_id")
    private long groupId;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "status")
    private long status;


    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

}
