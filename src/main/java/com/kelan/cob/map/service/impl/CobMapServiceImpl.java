package com.kelan.cob.map.service.impl;

import com.kelan.cob.map.dao.CobMapDao;
import com.kelan.cob.map.entity.CobMap;
import com.kelan.cob.map.service.CobMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 *
 */
@Service
public class CobMapServiceImpl implements CobMapService {

    @Autowired
    private CobMapDao dao;

    @Override
    public boolean addCcbMap(CobMap cobMap)  {
        return dao.addCcbMap(cobMap)==1?true:false;
    }

    @Override
    public List<CobMap> listCcbMap(int pageStart, int pageSize) {
        return dao.listCcbMap((pageStart-1)*pageSize, pageSize);
    }
}
