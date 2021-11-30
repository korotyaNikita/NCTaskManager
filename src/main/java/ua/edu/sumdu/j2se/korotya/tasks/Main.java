package ua.edu.sumdu.j2se.korotya.tasks;

import java.util.Iterator;

public class Main {

	public static void main(String[] args) {
		Task task1 = new Task("SSS", 30);
		Task task2 = new Task("sdadssad", 1, 3, 3);
		Task task3 = new Task("s", 1, 20, 3);
		task2.setActive(true);

		LinkedTaskList a = new LinkedTaskList();
		LinkedTaskList b = new LinkedTaskList();
		a.add(task1);
		a.add(task2);
		a.add(task3);

		b.add(task1);
		b.add(task2);

		System.out.println(a);
		System.out.println(b);
		Iterator<Task> i = a.iterator();

		for (Task o: a) {
			System.out.println(o.hashCode());
		}

		i.next();
		i.next();
		i.remove();

		System.out.println();
		for (Task o: a) {
			System.out.println(o);
		}
	}
}
