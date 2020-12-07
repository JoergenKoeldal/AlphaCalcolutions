package easyon.alphacalcolutions.model;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;

public class Project{
    private int projectId;
    private String title;
    private int projectLeaderId;
    private int[] assignedUserIds;
    private LocalDate startDate;
    private LocalDate endDate;
    private int projectCost;
    private int projectDuration;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProjectLeaderId() {
        return projectLeaderId;
    }

    public void setProjectLeaderId(int projectLeaderId) {
        this.projectLeaderId = projectLeaderId;
    }

    public int[] getAssignedUserIds() {
        return assignedUserIds;
    }

    public void setAssignedUserIds(int[] assignedUserIds) {
        this.assignedUserIds = assignedUserIds;
    }

    public void setAssignedUserIds(String[] assignedUserIdStrings) {
        this.assignedUserIds = new int[assignedUserIdStrings.length];
        int id;
        for(int i = 0; i < assignedUserIdStrings.length; i++){
            id = Integer.parseInt(assignedUserIdStrings[i]);
            this.assignedUserIds[i] = id;
        }
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        if (startDate != null) {
            this.startDate = LocalDate.parse(startDate);
        }
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        if (endDate != null) {
            this.endDate = LocalDate.parse(endDate);
        }
    }

    public int getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(int projectCost) {
        this.projectCost = projectCost;
    }

    public int getProjectDuration() {
        return projectDuration;
    }

    public void setProjectDuration(int projectDuration) {
        this.projectDuration = projectDuration;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project p = (Project) o;
        return projectId == p.getProjectId()
                && title.equals(p.getTitle())
                && projectLeaderId == p.getProjectLeaderId()
                && Arrays.equals(assignedUserIds, p.getAssignedUserIds())
                && startDate.equals(p.getStartDate())
                && endDate.equals(p.getEndDate());
    }
}
