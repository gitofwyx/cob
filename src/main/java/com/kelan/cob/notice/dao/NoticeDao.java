package com.kelan.cob.notice.dao;

import com.kelan.cob.notice.entity.Notice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface NoticeDao {

    public int addNotice(Notice notice);

    public Notice getNotice(String id);

    public List<Notice> listNotice(int pageStart, int pageSize);

    public int updateNotice(Notice notice);

    public int deleteListNotice(List<String> listStr);

    public int deleteNotice(List<String> listStr);
}
