package ua.edu.sumdu.j2se.korotya.tasks.model;

public class TaskListFactory {
    public static AbstractTaskList createTaskList(ListTypes.types type) {
        if (type == ListTypes.types.ARRAY) {
            return new ArrayTaskList();
        }
        return new LinkedTaskList();
    }
}
