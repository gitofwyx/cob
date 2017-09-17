package com.kelan.cob.member.dao;

import com.kelan.cob.member.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface MemberDao {

    public int addMember(Member member);

    public Member getMember(String id);

    public List<Member> listMember(int pageStart, int pageSize);

    public int updateMember(Member member);

    public int deleteListMember(List<String> listStr);

    public int deleteMember(List<String> listStr);
}
