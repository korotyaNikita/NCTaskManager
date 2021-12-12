package ua.edu.sumdu.j2se.korotya.tasks;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

public class ArrayTaskList extends AbstractTaskList implements Cloneable {
    private Task[] tasks = new Task[10];
    private int size;

    @Override
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

    @Override
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

    @Override
    public int size() {
        return size;
    }

    @Override
    public Task getTask(int index) throws IndexOutOfBoundsException {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();

        return tasks[index];
    }

    @Override
    public ListTypes.types getType() {
        return ListTypes.types.ARRAY;
    }

    @Override
    public Iterator<Task> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<Task> {
        int cursor;
        int lastReturned = -1;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public Task next() {
            int i = cursor;
            if (i >= size)
                throw new IndexOutOfBoundsException();

            cursor++;
            return tasks[lastReturned = i];
        }

        @Override
        public void remove() {
            if (lastReturned < 0)
                throw new IllegalStateException();

            ArrayTaskList.this.remove(getTask(lastReturned));
            cursor = lastReturned;
            lastReturned = -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayTaskList that = (ArrayTaskList) o;
        if (size != that.size) return false;

        for (int i = 0; i < size; i++) {
            if (!tasks[i].equals(that.tasks[i]))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        for (int i = 0; i < size; i++) {
            hash = 16 * hash + tasks[i].hashCode();
        }

        return hash;
    }

    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList clone = (ArrayTaskList) super.clone();
        clone.tasks = Arrays.copyOf(tasks, size);
        for (int i = 0; i < size; i++) {
            clone.tasks[i] = tasks[i].clone();
        }
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("ArrayTaskList{\n");
        for (Task task: this) {
            stringBuilder.append(task).append(",").append("\n");
        }
        stringBuilder.append("}\n");

        return stringBuilder.toString();
    }

    @Override
    public Stream<Task> getStream() {
        return Arrays.stream(tasks, 0, size);
    }
}
