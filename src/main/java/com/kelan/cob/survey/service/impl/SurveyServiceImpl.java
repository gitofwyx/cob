package com.kelan.cob.survey.service.impl;


import com.kelan.cob.survey.dao.SurveyDao;
import com.kelan.cob.survey.entity.Survey;
import com.kelan.cob.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyDao dao;

    @Override
    public boolean addSurvey(Survey survey) {
        return dao.addSurvey(survey)==1?true:false;
    }

    @Override
    public Survey getSurvey(String id) {
        return  dao.getSurvey(id);
    }

    @Override
    public List<Survey> listSurvey(int pageStart, int pageSize) {
        return dao.listSurvey((pageStart-1)*pageSize, pageSize);
    }

    @Override
    public boolean updateSurvey(Survey survey) {
        return dao.updateSurvey(survey)==1?true:false;
    }

    @Override
    public boolean deleteListSurvey(List<String> listStr) {
        return dao.deleteListSurvey(listStr)>=1?true:false;
    }

    @Override
    public boolean deleteSurvey(List<String> listStr) {
        return dao.deleteSurvey(listStr)==1?true:false;
    }
}
