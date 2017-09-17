package com.kelan.cob.notice.service.impl;


import com.kelan.cob.notice.dao.NoticeDao;
import com.kelan.cob.notice.entity.Notice;
import com.kelan.cob.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class NoticeServiceImpl  implements NoticeService {

    @Autowired
    private NoticeDao dao;

    @Override
    public boolean addNotice(Notice notice) {
        return dao.addNotice(notice)==1?true:false;
    }

    @Override
    public Notice getNotice(String id) {
        return  dao.getNotice(id);
    }

    @Override
    public List<Notice> listNotice(int pageStart, int pageSize) {
        return dao.listNotice((pageStart-1)*pageSize, pageSize);
    }

    @Override
    public boolean updateNotice(Notice develop) {
        return dao.updateNotice(develop)==1?true:false;
    }

    @Override
    public boolean deleteListNotice(List<String> listStr) {
        return dao.deleteListNotice(listStr)>=1?true:false;
    }

    @Override
    public boolean deleteNotice(List<String> listStr) {
        return dao.deleteNotice(listStr)==1?true:false;
    }
}
