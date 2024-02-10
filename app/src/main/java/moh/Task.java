package moh;

public class Task{
    private int taskID;
    private int stdID;
    private int courseID;
    private String taskType;
    private String taskDes;
    private String taskPriority;
    private String taskDueDate;
    private String taskDueTime;


    public Task(int taskID, int stdID, int courseID, String taskType, String taskDes,
                String taskPriority, String taskDueDate, String taskDueTime) {
        this.taskID = taskID;
        this.stdID = stdID;
        this.courseID = courseID;
        this.taskType = taskType;
        this.taskDes = taskDes;
        this.taskPriority = taskPriority;
        this.taskDueDate = taskDueDate;
        this.taskDueTime = taskDueTime;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getStdID() {
        return stdID;
    }

    public void setStdID(int stdID) {
        this.stdID = stdID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskDes() {
        return taskDes;
    }

    public void setTaskDes(String taskDes) {
        this.taskDes = taskDes;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(String taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public String getTaskDueTime() {
        return taskDueTime;
    }

    public void setTaskDueTime(String taskDueTime) {
        this.taskDueTime = taskDueTime;
    }

    public String getTitle() {
        String taskTitle = "";
        taskTitle = courseID + taskType;
        return taskTitle;
    }
}

