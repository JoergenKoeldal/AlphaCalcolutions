package easyon.alphacalcolutions.data;

import easyon.alphacalcolutions.model.Project;
import easyon.alphacalcolutions.model.Task;
import easyon.alphacalcolutions.model.UserTitle;
import easyon.alphacalcolutions.model.User;
import easyon.alphacalcolutions.repository.ProjectDAO;
import easyon.alphacalcolutions.repository.TaskDAO;
import easyon.alphacalcolutions.repository.UserDAO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataFacade implements IDataFacade{

    private final UserDAO USER_DAO = new UserDAO(DBManager.getConnection());
    private final ProjectDAO PROJECT_DAO = new ProjectDAO(DBManager.getConnection());
    private final TaskDAO TASK_DAO = new TaskDAO(DBManager.getConnection());


    //----------------------------- USER -------------------------------------

    public void createUser(User user){
        USER_DAO.createUser(user);
    }

    public ArrayList<User> getUserList(){
       return USER_DAO.getUserList();
    }

    public User getUser(int userId){
        return USER_DAO.getUser(userId);
    }

    public ArrayList<User> getUsersById(ArrayList<Integer> userIds){
        return USER_DAO.getUsersByIds(userIds);
    }



    //----------------------------- USER TITLE -------------------------------------

    public ArrayList<UserTitle> getUserTitleList(){
        return USER_DAO.getUserTitleList();
    }

    //----------------------------- PROJECT -------------------------------------

    public void createProject(Project project) {PROJECT_DAO.createProject(project);
    }

    public ArrayList<Project> getProjectList() {return PROJECT_DAO.getProjectList();}

    public Project getProject (int projectId) {return PROJECT_DAO.getProject(projectId);}

    //----------------------------- TASK -------------------------------------

    public void createTask(Task task) {
        TASK_DAO.createTask(task);
    }

    public ArrayList<Task> getTaskList(){
        return TASK_DAO.getTaskList();
    }

    public Task getTaskById(int taskId){
        return TASK_DAO.getTaskById(taskId);
    }





}
