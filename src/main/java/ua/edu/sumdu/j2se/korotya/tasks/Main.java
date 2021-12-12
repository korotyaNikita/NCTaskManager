package ua.edu.sumdu.j2se.korotya.tasks;

import java.io.File;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) throws CloneNotSupportedException {
		LocalDateTime dateTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
		Task task1 = new Task("dddd", dateTime, dateTime.plusDays(1), 3600);
		task1.setActive(true);
		System.out.println(task1);
		System.out.println(task1.nextTimeAfter(dateTime.minusDays(1)));

		Task task4 = new Task("Обід", LocalDateTime.of(2020, Month.AUGUST, 24, 16, 0));

		Task task2 = new Task("Ранкова пробіжка",
				LocalDateTime.of(2020, Month.MARCH, 1, 8, 15),
				LocalDateTime.of(2020, Month.SEPTEMBER, 1, 8, 15),
				86400);

		Task task3 = new Task("Приймання ліків",
				LocalDateTime.of(2020, Month.AUGUST, 20, 8, 15),
				LocalDateTime.of(2020, Month.AUGUST, 28, 8, 15),
				43200);

		Task task5 = new Task("Зустріч",
				LocalDateTime.of(2020, Month.SEPTEMBER, 1, 8, 15));

		task1.setActive(true);
		task2.setActive(true);
		task3.setActive(true);
		task5.setActive(true);

		ArrayTaskList a = new ArrayTaskList();
		LinkedTaskList b = new LinkedTaskList();
		a.add(task4);
		a.add(task2);
		a.add(task3);
		a.add(task5);
		a.add(task5);
		a.add(task5);
		a.add(task1);
		a.add(task2);


		b.add(task4);
		b.add(task2);
		System.out.println(a);
		System.out.println(a.clone().hashCode());
		System.out.println(b.hashCode());
		System.out.println(a.getStream().count());
		System.out.println(a.getStream().collect(Collectors.toList()));


		a.add(task3);
		a.add(task5);
		a.add(task2);
		System.out.println(a);
		System.out.println(a.clone());
		System.out.println(b);

		System.out.println("Incoming");
		System.out.println(Tasks.incoming(a,
				LocalDateTime.of(2020, Month.AUGUST, 25, 8, 0),
				LocalDateTime.of(2020, Month.AUGUST, 26, 8, 0)));

		SortedMap<LocalDateTime, Set<Task>> result = Tasks.calendar(a,
				LocalDateTime.of(2020, Month.AUGUST, 25, 8, 0),
				LocalDateTime.of(2020, Month.SEPTEMBER, 2, 8, 0));

		for (LocalDateTime key: result.keySet()) {
			System.out.print(key + " = ");
			System.out.print(result.get(key));
			System.out.println();
		}

		Iterator<Task> i = a.iterator();
		for (Task o: a) {
			System.out.println(o.hashCode());
		}

		i.next();
		i.next();
		i.remove();

		System.out.println();
		System.out.println(a);

		File file = new File("d:\\file.txt");
		TaskIO.writeBinary(a, file);
		LinkedTaskList linkedTaskList = new LinkedTaskList();
		TaskIO.readBinary(linkedTaskList, file);
		System.out.println(linkedTaskList);

		file = new File("d:\\js.json");
		TaskIO.writeText(a, file);
		linkedTaskList = new LinkedTaskList();
		TaskIO.readText(linkedTaskList, file);
		System.out.println(linkedTaskList);
	}
}
