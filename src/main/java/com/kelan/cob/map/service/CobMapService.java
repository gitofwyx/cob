package com.kelan.cob.map.service;

import com.kelan.cob.map.entity.CobMap;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface CobMapService {

    public boolean addCcbMap(CobMap cobMap);

    public List<CobMap> listCcbMap(int pageStart, int pageSize);
}
