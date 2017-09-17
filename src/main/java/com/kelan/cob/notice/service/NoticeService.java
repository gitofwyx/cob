package com.kelan.cob.notice.service;

import com.kelan.cob.notice.entity.Notice;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface NoticeService {

    public boolean addNotice(Notice notice);

    public Notice getNotice(String id);

    public List<Notice> listNotice(int pageStart, int pageSize);

    public boolean updateNotice(Notice develop);

    public boolean deleteListNotice(List<String> listStr);

    public boolean deleteNotice(List<String> listStr);
}
