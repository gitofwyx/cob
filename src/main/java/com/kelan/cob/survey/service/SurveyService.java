package com.kelan.cob.survey.service;

import com.kelan.cob.survey.entity.Survey;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface SurveyService {

    boolean addSurvey(Survey survey);

    Survey getSurvey(String id);

    List<Survey> listSurvey(int pageStart, int pageSize);

    public boolean updateSurvey(Survey survey);

    public boolean deleteListSurvey(List<String> listStr);

    public boolean deleteSurvey(List<String> listStr);
}
