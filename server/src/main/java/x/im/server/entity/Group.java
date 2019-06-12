package x.im.server.entity;

import javax.persistence.*;

@Entity
@Table(name = "group")
public class Group {

 @Id
 @Column(name = "group_id")
  private long groupId;
 @Column(name = "owner_id")
  private long ownerId;
 @Column(name = "create_time")
  private long createTime;
 @Column(name = "status")
  private long status;
 @Column(name = "type")
  private long type;


  public long getGroupId() {
    return groupId;
  }

  public void setGroupId(long groupId) {
    this.groupId = groupId;
  }


  public long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(long ownerId) {
    this.ownerId = ownerId;
  }


  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }


  public long getType() {
    return type;
  }

  public void setType(long type) {
    this.type = type;
  }

}
