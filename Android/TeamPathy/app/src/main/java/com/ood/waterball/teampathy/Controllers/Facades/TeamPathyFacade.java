package com.ood.waterball.teampathy.Controllers.Facades;


import com.ood.waterball.teampathy.Controllers.MyUtils.DateConvertStrategy.DateConvertStrategy;
import com.ood.waterball.teampathy.Domains.Issue;
import com.ood.waterball.teampathy.Domains.Project;

import java.util.Date;
import java.util.List;

public abstract class TeamPathyFacade {
    private DateConvertStrategy dateConvertStrategy;
    public TeamPathyFacade(DateConvertStrategy dateConvertStrategy){
        this.dateConvertStrategy = dateConvertStrategy;
    }

    public String convertDateToString(Date datetime){
        return dateConvertStrategy.dateToTime(datetime);
    }

    public abstract List<Project> getProjectListByUserId(String userId) throws Exception;

    public abstract Project getProjectById(String projectId) throws Exception;
    public abstract List<Issue> getIssueListByProjectId(String projectId) throws Exception;
    public abstract Issue getIssueById(String issueId)throws Exception;
    public abstract Issue addIssue(Issue issue)throws Exception;
    public abstract Issue removeIssue(Issue issue)throws Exception;
    public abstract Issue editIssue(Issue issue)throws Exception;
    public abstract String[] getIssueTypeListByProjectId(String projectId) throws Exception;
}
