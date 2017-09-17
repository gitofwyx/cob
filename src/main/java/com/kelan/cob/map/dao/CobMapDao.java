package com.kelan.cob.map.dao;

import com.kelan.cob.map.entity.CobMap;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface CobMapDao {

    public int addCcbMap(CobMap cobMap);

    public List<CobMap> listCcbMap(int pageStart, int pageSize);
}
