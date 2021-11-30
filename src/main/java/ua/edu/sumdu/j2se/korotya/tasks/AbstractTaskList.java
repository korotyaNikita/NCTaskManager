package ua.edu.sumdu.j2se.korotya.tasks;

public abstract class AbstractTaskList implements java.lang.Iterable<Task> {
    public abstract void add(Task task) throws NullPointerException;
    public abstract boolean remove(Task task);
    public abstract int size();
    public abstract Task getTask(int index) throws IndexOutOfBoundsException;
    public abstract ListTypes.types getType();

    public AbstractTaskList incoming(int from, int to) {
        AbstractTaskList taskList = TaskListFactory.createTaskList(getType());
        Task task;

        for (int i = 0; i < taskList.size(); i++) {
            task = taskList.getTask(i);
            if (task.nextTimeAfter(from) != -1 && task.nextTimeAfter(to) == -1)
                taskList.add(task);
        }

        return taskList;
    }
}
