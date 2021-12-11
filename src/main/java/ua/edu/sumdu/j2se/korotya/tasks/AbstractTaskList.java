package ua.edu.sumdu.j2se.korotya.tasks;

import java.util.stream.Stream;

public abstract class AbstractTaskList implements java.lang.Iterable<Task> {
    public abstract void add(Task task) throws NullPointerException;
    public abstract boolean remove(Task task);
    public abstract int size();
    public abstract Task getTask(int index) throws IndexOutOfBoundsException;
    public abstract ListTypes.types getType();

    public abstract Stream<Task> getStream();

    /*
    public final AbstractTaskList incoming(LocalDateTime from, LocalDateTime to) {
        AbstractTaskList taskList = TaskListFactory.createTaskList(getType());
        Stream<Task> taskStream = this.getStream()
                .filter(task -> (task.nextTimeAfter(from) != null && task.nextTimeAfter(from) < to));

        taskStream.forEach(taskList::add);
        return taskList;
    }
    */
}
