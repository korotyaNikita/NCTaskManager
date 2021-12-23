package ua.edu.sumdu.j2se.korotya.tasks.view;

import ua.edu.sumdu.j2se.korotya.tasks.model.*;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;

public class View {
    private enum ChangeType {
        EMPTY,
        RENAME,
        ACTIVE,
        TIME,
        START_TIME,
        END_TIME,
        REPEAT_INTERVAL
    }
    Scanner reader;
    private static final Logger log = Logger.getLogger(View.class);

    public int getAction() {
        reader = new Scanner(System.in);
        int value = -1;
        boolean flag = true;
        while (flag) {
            try {
                value = Integer.parseInt(reader.nextLine());
                if (value < 0 || value > 5) {
                    throw new Exception("Введите правильный номер ");
                }
                flag = false;
            }
            catch (NumberFormatException e) {
                System.out.println("Введите правильный номер: ");
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return value;
    }

    public Task addTask() {
        Task task;
        System.out.println("Введите название задачи (0 - отмена):");
        String title = reader.nextLine();
        if ("0".equals(title)) {
            return null;
        }
        System.out.println("Задача выполняется больше одного раза? (true/false)");
        boolean repeat = Boolean.parseBoolean(reader.nextLine());
        if (!repeat) {
            System.out.println("Введите дату выполнения задачи (формат: dd-MM-yyyy HH:mm:ss) (0 - отмена): ");
            LocalDateTime time = inputDate();
            if (time == null) {
                return null;
            }
            task = new Task(title, time);
        }
        else {
            System.out.println("Введите дату начала выполнения задачи(format: dd-MM-yyyy HH:mm:ss) (0 - отмена): ");
            LocalDateTime startTime = inputDate();
            if (startTime == null) {
                return null;
            }
            System.out.println("Введите дату окончания выполнения задачи(format: dd-MM-yyyy HH:mm:ss) (0 - отмена): ");
            LocalDateTime endTime = inputDate();
            if (endTime == null) {
                return null;
            }
            while (startTime.isAfter(endTime)) {
                System.out.println("Задача не может закончится раньше, чем началась");
                log.info("Задача не может закончится раньше, чем началась");
                endTime = inputDate();
            }
            System.out.println("Введите интервал повтора(в секундах): ");
            int interval = Integer.parseInt(reader.nextLine());
            task = new Task(title, startTime, endTime, interval);
        }
        System.out.println("Вы хотите сделать задачу активной? true/false ");
        boolean isActive = Boolean.parseBoolean(reader.nextLine());
        task.setActive(isActive);
        System.out.println("Задача " + task.getTitle() + " добавлена");
        return task;
    }

    public void removeTask(AbstractTaskList taskList) {
        System.out.println("Введите название задачи для удаления (0 - отмена): ");
        String title = reader.nextLine();

        for (Task task : taskList) {
            if (Objects.equals(task.getTitle(), title)) {
                taskList.remove(task);
                System.out.println("Задача " + task.getTitle() + " удалена");
                log.info("Задача " + task.getTitle() + " удалена");
                return;
            }
        }
        System.out.println("Задача " + title + " не найдена");
        log.info("Задача " + title + " не найдена");
    }

    public void changeTask(AbstractTaskList taskList) {
        System.out.println("Введите название задачи для редактирования (0 - отмена): ");
        String title = reader.nextLine();

        for (Task task : taskList) {
            if (Objects.equals(task.getTitle(), title)) {
                System.out.println(task);
                System.out.println("Выберите, что хотите изменить: ");
                System.out.println("1. Переименовать");
                System.out.println("2. Сделать активной/Неактивной");
                System.out.println("3. Изменить дату выполнения");
                System.out.println("4. Изменить дату начала выполнения");
                System.out.println("5. Изменить дату окончания выполнения");
                System.out.println("6. Изменить интервал повтора");
                System.out.println("0. Отмена");
                LocalDateTime dateTime;
                String result = "";
                ChangeType changeType = ChangeType.EMPTY;
                while (changeType.equals(ChangeType.EMPTY)) {
                    int value = Integer.parseInt(reader.nextLine());
                    switch (value) {
                        case 0:
                            return;
                        case 1:
                            System.out.println("Введите название задачи (0 - отмена): ");
                            result = reader.nextLine();
                            if ("0".equals(result)) {
                                return;
                            }
                            task.setTitle(result);
                            changeType = ChangeType.RENAME;
                            break;
                        case 2:
                            System.out.println("Сделать активной? true/false (0 - отмена): ");
                            result = reader.nextLine();
                            if ("0".equals(result)) {
                                return;
                            }
                            task.setActive(Boolean.parseBoolean(result));
                            changeType = ChangeType.ACTIVE;
                            break;
                        case 3:
                            System.out.println("Введите дату выполнения задачи (формат: dd-MM-yyyy HH:mm:ss) (0 - отмена):");
                            dateTime = inputDate();
                            task.setTime(dateTime);
                            changeType = ChangeType.TIME;
                            break;
                        case 4:
                            System.out.println("Введите дату начала выполнения задачи(format: dd-MM-yyyy HH:mm:ss) (0 - отмена): ");
                            dateTime = inputDate();
                            task.setTime(dateTime, task.getEndTime(), task.getRepeatInterval());
                            changeType = ChangeType.START_TIME;
                            break;
                        case 5:
                            System.out.println("Введите дату окончания выполнения задачи(format: dd-MM-yyyy HH:mm:ss) (0 - отмена): ");
                            dateTime = inputDate();
                            task.setTime(task.getStartTime(), dateTime, task.getRepeatInterval());
                            changeType = ChangeType.END_TIME;
                            break;
                        case 6:
                            System.out.println("Введите интервал повтора(в секундах): ");
                            result = reader.nextLine();
                            task.setTime(task.getStartTime(), task.getEndTime(), Integer.parseInt(result));
                            changeType = ChangeType.REPEAT_INTERVAL;
                            break;
                        default:
                            System.out.println("Ошибка. Введите правильный номер: ");
                    }
                }
                System.out.println("У задачи " + title + " изменен " + changeType + " на " + result);
                log.info("У задачи " + title + " изменен " + changeType + " на " + result);
                return;
            }
        }
        System.out.println("Задача " + title + " не найдена");
        log.info("Задача " + title + " не найдена.");
    }

    public void showTasks(AbstractTaskList taskList) {
        System.out.println(taskList);
        log.info("Показаны все задачи");
        System.out.println("Введите любой символ");
        reader.nextLine();
    }

    public void mainMenu() {
        System.out.println("1. Показать все задачи");
        System.out.println("2. Добавить задачу");
        System.out.println("3. Удалить задачу");
        System.out.println("4. Изменить задачу");
        System.out.println("5. Календарь задач");
        System.out.println("0. Выйти");
    }

    public void calendar(AbstractTaskList taskList) {
        System.out.println("Введите первую дату в календаре дату(format: dd-MM-yyyy HH:mm:ss) (0 - отмена): ");
        LocalDateTime start = inputDate();

        System.out.println("Введите последнюю дату в календаре дату(format: dd-MM-yyyy HH:mm:ss) (0 - отмена): ");
        LocalDateTime end = inputDate();
        while (start.isAfter(end)) {
            System.out.println("Задача не может закончится раньше, чем началась");
            log.info("Задача не может закончится раньше, чем началась");
            end = inputDate();
        }

        SortedMap<LocalDateTime, Set<Task>> sortedMap = Tasks.calendar(taskList, start, end);
        if (sortedMap.entrySet().size() == 0) {
            System.out.println("Не найдено ни одной задачи.");
            return;
        }
        System.out.println("Дата                 | Задача         ");
        Object[] tasks;
        for (SortedMap.Entry<LocalDateTime, Set<Task>> entry : sortedMap.entrySet()) {
            tasks = entry.getValue().toArray();
            System.out.print(entry.getKey().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss")) + " | ");
            for (int i = 0; i < tasks.length; i++) {
                if (i + 1 == tasks.length) {
                    System.out.print(((Task) tasks[i]).getTitle() + ".");
                } else {
                    System.out.print(((Task) tasks[i]).getTitle() + ", ");
                }
            }
            System.out.println();
        }
        System.out.println("Введите любой символ");
        reader.nextLine();
    }

    private LocalDateTime inputDate() {
        while (true) {
            try {
                String date = reader.nextLine();
                if ("0".equals(date)) {
                    return null;
                }
                return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            }
            catch (DateTimeParseException e) {
                System.out.println("Введите дату в правильном формате (формат: dd-MM-yyyy HH:mm:ss): ");
                log.info("Неправильний формат даты.");
            }
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
