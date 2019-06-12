package x.im.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import x.im.common.Const;
import x.im.server.dao.GroupMemberRepository;
import x.im.server.dao.GroupRepository;
import x.im.server.entity.Group;
import x.im.server.entity.GroupMember;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupMemberRepository groupMemberRepository;

    public Long addGroup(Long userId) {
        Group group = new Group();
        group.setOwnerId(userId);
        group.setCreateTime(System.currentTimeMillis());
        group.setType(Const.CONVERSATION_TYPE_GROUP);
        groupRepository.save(group);

        addGroupMember(group.getGroupId(), userId);
        return group.getGroupId();
    }


    public List<GroupMember> getGroupMember(Long groupId) {
        return groupMemberRepository.findAllByGroupId(groupId);
    }

    public void addGroupMember(Long groupId, Long userId) {
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(groupId);
        groupMember.setUserId(userId);
        groupMemberRepository.save(groupMember);
    }

}
