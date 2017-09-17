package com.kelan.cob.member.service.impl;

import com.kelan.cob.member.dao.MemberDao;
import com.kelan.cob.member.entity.Member;
import com.kelan.cob.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao dao;

    @Override
    public boolean addMember(Member member) {
        return dao.addMember(member)==1?true:false;
    }

    @Override
    public Member getMember(String id) {
        return  dao.getMember(id);
    }

    @Override
    /**
     * 分页获取列表
     */
    public List<Member> listMember(int pageStart, int pageSize) {
        return dao.listMember((pageStart-1)*pageSize, pageSize);
        //return dao.listActivity(pageStart,pageSize);
    }

    @Override
    public boolean updateMember(Member member) {
        return dao.updateMember(member)==1?true:false;
    }

    @Override
    public boolean deleteListMember(List<String> listStr)  {
        return dao.deleteListMember(listStr)>=1?true:false;
    }

    @Override
    public boolean deleteMember(List<String> listStr) {
        return dao.deleteMember(listStr)>1?true:false;
    }
}
