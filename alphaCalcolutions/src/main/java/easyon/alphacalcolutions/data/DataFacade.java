package easyon.alphacalcolutions.data;

import easyon.alphacalcolutions.model.Project;
import easyon.alphacalcolutions.model.Task;
import easyon.alphacalcolutions.model.UserTitle;
import easyon.alphacalcolutions.model.User;
import easyon.alphacalcolutions.repository.ProjectDAO;
import easyon.alphacalcolutions.repository.TaskDAO;
import easyon.alphacalcolutions.repository.UserDAO;
import easyon.alphacalcolutions.repository.exception.CreateTaskHasDependencyException;
import easyon.alphacalcolutions.repository.exception.CreateUserHasTaskException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataFacade implements IDataFacade {

    private final UserDAO USER_DAO = new UserDAO(DBManager.getConnection());
    private final ProjectDAO PROJECT_DAO = new ProjectDAO(DBManager.getConnection());
    private final TaskDAO TASK_DAO = new TaskDAO(DBManager.getConnection());


    //----------------------------- USER -------------------------------------

    public void createUser(User user) {
        USER_DAO.createUser(user);
    }

    public ArrayList<User> getUserList() {
        return USER_DAO.getUserList();
    }

    public User getUserById(int userId) {
        return USER_DAO.getUserById(userId);
    }

    public ArrayList<User> getUsersById(int[] userIds) {
        return USER_DAO.getUsersByIds(userIds);
    }

    public boolean updateUser(User user) {
        return USER_DAO.updateUser(user);
    }

    public boolean deleteUser(int userId){
        return USER_DAO.deleteUser(userId);
    }


    //----------------------------- USER TITLE -------------------------------------

    public ArrayList<UserTitle> getUserTitleList() {
        return USER_DAO.getUserTitleList();
    }

    //----------------------------- PROJECT -------------------------------------

    public void createProject(Project project) {
        PROJECT_DAO.createProject(project);
    }

    public ArrayList<Project> getProjectList() {
        ArrayList<Project> projects = PROJECT_DAO.getProjectList();
        for (Project project : projects) {
            project.setProjectCost(getProjectCost(project.getProjectId()));
            project.setProjectDuration(getProjectDuration(project.getProjectId()));
        }
        return projects;
    }


    public Project getProject(int projectId) {
        return PROJECT_DAO.getProject(projectId);
    }

    public int getProjectCost(int projectId) {
        int projectTotalCost = 0;

        for (Task task : getTaskList(projectId)) {
            LocalDate startDate = task.getStartDate();
            LocalDate endDate = task.getEndDate();
            int daysWorked = (int) ChronoUnit.DAYS.between(startDate, endDate);
            int totalHoursWorked = daysWorked * 8;
            for (User user : task.getAssignedUsers()) {
                int hourlySalary = user.getHourlySalary();
                projectTotalCost += totalHoursWorked * hourlySalary;
            }
        }
        return projectTotalCost;
    }

    public int getProjectDuration(int projectId) {
        Project project = getProject(projectId);
        if (project.getStartDate() == null || project.getEndDate() == null) return 0;
        return (int) ChronoUnit.DAYS.between(project.getStartDate(), project.getEndDate());
    }

    public HashMap<String, Integer> getTitleHours(int projectId) {

        ArrayList<Task> tasks = getTaskList(projectId);

        HashMap<String, Integer> hashMap = new HashMap<>();

        for (UserTitle userTitle : USER_DAO.getUserTitleList()) {
            hashMap.put(userTitle.getUserTitle(), 0);
        }


        for (Task task : tasks) {
            for (User u : task.getAssignedUsers()) {
                LocalDate startDate = task.getStartDate();
                LocalDate endDate = task.getEndDate();
                int oldValue = hashMap.get(u.getTitle().getUserTitle());
                int hours = (int) ChronoUnit.DAYS.between(startDate, endDate) * 8;
                hashMap.replace(u.getTitle().getUserTitle(), oldValue + hours);

            }
        }
        return hashMap;
    }

    //Try to split title hours out on each worker
    public HashMap<User, Integer> getUserHours(int projectId) {
        Project project = getProject(projectId);
        ArrayList<Task> tasks = getTaskList(projectId);
        ArrayList<User> users = USER_DAO.getUsersByIds(project.getAssignedUserIds());

        HashMap<String, Integer> hashMap = new HashMap<>();
        HashMap<User, Integer> hashMap1 = new HashMap<>();


        for (User user : users) {
            hashMap.put(user.getUsername(), 0);
        }


        for (Task task : tasks) {
            for (User user : task.getAssignedUsers()) {
                LocalDate startDate = task.getStartDate();
                LocalDate endDate = task.getEndDate();
                int oldValue = hashMap.get(user.getUsername());
                int hours = (int) ChronoUnit.DAYS.between(startDate, endDate) * 8;
                hashMap.replace(user.getUsername(), oldValue + hours);
            }
        }

        for (Map.Entry<String, Integer> entry : hashMap.entrySet()){
            for(User user : users){
                if (user.getUsername().equals(entry.getKey())){
                    hashMap1.put(user, entry.getValue());
                }
            }
        }

        return hashMap1;
    }

    //----------------------------- TASK -------------------------------------

    public void createTask(Task task) throws CreateUserHasTaskException, CreateTaskHasDependencyException {
        TASK_DAO.createTask(task);
    }

    public ArrayList<Task> getTaskList(int projectId) {

        ArrayList<Task> taskList = TASK_DAO.getTaskList(projectId);
        for (Task task : taskList) {
            task.setTaskLeader(getUserById(task.getTaskLeaderId()));
            task.setAssignedUsers(getUsersById(task.getAssignedUserIds()));
        }
        return taskList;
    }


    public Task getTaskById(int taskId) {
        return TASK_DAO.getTaskById(taskId);
    }


    public void updateProject(Project project) {
        PROJECT_DAO.updateProject(project);
    }

    public void deleteProject(int projectId) {
        PROJECT_DAO.deleteProject(projectId);
    }

    public void updateTask(Task task) throws CreateUserHasTaskException, CreateTaskHasDependencyException {
        TASK_DAO.updateTask(task);
    }

    public void deleteTask(int taskId) {
        TASK_DAO.deleteTask(taskId);
    }

}
