package ua.edu.sumdu.j2se.korotya.tasks;

public class LinkedTaskList {
    private Node first;
    private Node last;
    private int size;

    private static class Node {
        Task task;
        Node prev;
        Node next;

        Node(Task task) {
            this.task = task;
        }
    }

    public void add(Task task) throws NullPointerException {
        if(task == null)
            throw new NullPointerException();

        Node node = new Node(task);

        if (first == null)
            first = node;
        else {
            last.next = node;
            node.prev = last;
        }
        last = node;
        size++;
    }

    public boolean remove(Task task) {
        for (Node temp = first; temp != null; temp = temp.next) {
            if (temp.task == task) {
                if (temp.prev == null) {
                    first = temp.next;
                }
                else if (temp.next == null) {
                    last = temp.prev;
                }
                else {
                    temp.next.prev = temp.prev;
                    temp.prev.next = temp.next;
                }

                size--;
                return true;
            }
        }

        return false;
    }

    public int size() {
        return size;
    }

    public Task getTask(int index) throws IndexOutOfBoundsException {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();

        Node temp;

        if (index < (size / 2)) {
            temp = first;
            for (int i = 0; i < index; i++)
                temp = temp.next;
        }
        else {
            temp = last;
            for (int i = size - 1; i > index; i--)
                temp = temp.prev;
        }

        return temp.task;
    }

    public LinkedTaskList incoming(int from, int to) {
        LinkedTaskList linkedTaskList = new LinkedTaskList();
        for (Node temp = first; temp != null; temp = temp.next)
            if (temp.task.nextTimeAfter(from) != -1 && temp.task.nextTimeAfter(to) == -1)
                linkedTaskList.add(temp.task);

        return linkedTaskList;
    }
}
