package com.kelan.cob.survey.dao;

import com.kelan.cob.survey.entity.Survey;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface SurveyDao {

    public int addSurvey(Survey survey);

    public Survey getSurvey(String id);

    public List<Survey> listSurvey(int pageStart, int pageSize);

    public int updateSurvey(Survey survey);

    public int deleteListSurvey(List<String> listStr);

    public int deleteSurvey(List<String> listStr);
}
