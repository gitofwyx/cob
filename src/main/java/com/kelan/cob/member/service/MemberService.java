package com.kelan.cob.member.service;

import com.kelan.cob.member.entity.Member;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface MemberService {

    boolean addMember(Member member);

    Member getMember(String id);

    List<Member> listMember(int pageStart, int pageSize);

    public boolean updateMember(Member member);

    public boolean deleteListMember(List<String> listStr);

    public boolean deleteMember(List<String> listStr);
}
