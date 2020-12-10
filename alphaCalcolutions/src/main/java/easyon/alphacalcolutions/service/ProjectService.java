package easyon.alphacalcolutions.service;

import easyon.alphacalcolutions.data.DataFacade;
import easyon.alphacalcolutions.model.Project;
import easyon.alphacalcolutions.model.Task;
import easyon.alphacalcolutions.model.User;
import easyon.alphacalcolutions.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ProjectService {

    private final DataFacade dataFacade;

    public ProjectService(DataFacade dataFacade) {
        this.dataFacade = dataFacade;
    }

    public void createProject(Project project){
        dataFacade.createProject(project);
    }

    public ArrayList<Project> getProjectList() {
        ArrayList<Project> projects = dataFacade.getProjectList();
        for (Project project : projects) {
            project.setProjectCost(getProjectCost(project.getProjectId()));
        }
        return projects;
    }

    private int getProjectCost(int projectId) {
        int projectTotalCost = 0;
        int businessDays, totalHoursWorked, hourlySalary;
        for (Task task : dataFacade.getTaskList(projectId)) {
            businessDays = DateUtil.businessDaysBetween(task.getStartDate(), task.getEndDate());
            totalHoursWorked = businessDays * 8;
            for (User user : task.getAssignedUsers()) {
                hourlySalary = user.getHourlySalary();
                projectTotalCost += totalHoursWorked * hourlySalary;
            }
        }
        return projectTotalCost;
    }

    public Project getProject(int projectId) {return dataFacade.getProject(projectId);}

    public ArrayList<User> getAssignedUsersFromProject(int projectId){
        int[] assignedUsers = dataFacade.getProject(projectId).getAssignedUserIds();
        return dataFacade.getUsersById(assignedUsers);
    }

    public void updateProject(Project project) {
        dataFacade.updateProject(project);
    }

    public void deleteProject(int projectId) {
        dataFacade.deleteProject(projectId);
    }

    public HashMap<String , Integer> getTitleHours(int projectId){
        return dataFacade.getTitleHours(projectId);
    }

    public HashMap<User , Integer> getUserHours(int projectId){
        return dataFacade.getUserHours(projectId);
    }


}
