package ua.edu.sumdu.j2se.korotya.tasks;

public class ArrayTaskList {
    private Task[] tasks = new Task[10];
    private int size;

    public void add(Task task) throws NullPointerException {
        if(task == null)
            throw new NullPointerException();

        if (size >= tasks.length) {
            Task[] tasksCopy = tasks;
            tasks = new Task[tasks.length * 2];

            System.arraycopy(tasksCopy, 0, tasks, 0, tasksCopy.length);
        }

        tasks[size] = task;
        size++;
    }

    public boolean remove(Task task) {
        int index = -1;
        for (int i = 0; i < size; i++)
            if (tasks[i] == task) {
                index = i;
                break;
            }

        if (index == -1)
            return false;

        tasks[index] = null;
        for (int i = index + 1; i < size; i++) {
            tasks[i - 1] = tasks[i];
            tasks[i] = null;
        }
        size--;

        return true;
    }

    public int size() {
        return size;
    }

    public Task getTask(int index) throws IndexOutOfBoundsException {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();

        return tasks[index];
    }

    public ArrayTaskList incoming(int from, int to) {
        ArrayTaskList arrayTaskList = new ArrayTaskList();
        for (int i = 0; i < size; i++)
            if (tasks[i].nextTimeAfter(from) != -1 && tasks[i].nextTimeAfter(to) == -1)
                arrayTaskList.add(tasks[i]);

        return arrayTaskList;
    }
}
