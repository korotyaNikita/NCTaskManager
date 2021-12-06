package ua.edu.sumdu.j2se.korotya.tasks;

import java.util.Iterator;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		Task task1 = new Task("SSS", 30);
		Task task2 = new Task("sdadssad", 1, 10, 3);
		Task task3 = new Task("s", 1, 20, 3);
		task1.setActive(true);
		task2.setActive(true);
		task3.setActive(true);

		ArrayTaskList a = new ArrayTaskList();
		LinkedTaskList b = new LinkedTaskList();
		a.add(task1);
		a.add(task2);
		a.add(task3);

		b.add(task1);
		b.add(task2);
		System.out.println(a.getStream().count());
		System.out.println(a.getStream().collect(Collectors.toList()));


		System.out.println(a);
		System.out.println(b);

		System.out.println(a.incoming(10, 31));

		Iterator<Task> i = a.iterator();
		for (Task o: a) {
			System.out.println(o.hashCode());
		}

		i.next();
		i.next();
		i.remove();

		System.out.println();
		System.out.println(a);
	}
}
