package ua.edu.sumdu.j2se.korotya.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.korotya.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.korotya.tasks.model.Task;
import ua.edu.sumdu.j2se.korotya.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.korotya.tasks.view.Notification;
import ua.edu.sumdu.j2se.korotya.tasks.view.View;

import java.io.File;

public abstract class Controller {
    private static final Logger log = Logger.getLogger(Controller.class);
    protected View view;
    protected AbstractTaskList taskList;
    protected int action = -1;
    protected NotificationThread notificationThread;

    public Controller(View view, AbstractTaskList taskList) {
        this.view = view;
        this.taskList = taskList;

        notificationThread = new NotificationThread(taskList);
        Notification notification = new Notification();
        notificationThread.register(notification);
        notificationThread.start();
    }

    public abstract void process(Actions actions);

    public void addTask() {
        try {
            Task task = view.addTask();
            if (task == null) {
                throw new NullPointerException();
            }
            taskList.add(task);
            log.info("Задача " + task.getTitle() + " добавлена.");
        }
        catch (NullPointerException e) {
            view.showMessage("Операция отменена.");
            log.info("Операция отменена");
        }
        notificationThread.setTaskList(taskList);
        TaskIO.writeBinary(taskList, new File("tasks.txt"));
        backMenu();
    }

    public void removeTask() {
        view.removeTask(taskList);
        notificationThread.setTaskList(taskList);
        TaskIO.writeBinary(taskList, new File("tasks.txt"));
        backMenu();
    }

    public void changeTask() {
        view.changeTask(taskList);
        notificationThread.setTaskList(taskList);
        TaskIO.writeBinary(taskList, new File("tasks.txt"));
        backMenu();
    }

    public void showTasks() {
        view.showTasks(taskList);
        mainMenu();
    }

    public void calendar() {
        view.calendar(taskList);
        mainMenu();
    }

    public void mainMenu() {
        view.mainMenu();
        action = view.getAction();
        Actions actions = Actions.EMPTY;
        switch (action) {
            case 1: actions = Actions.SHOW_TASKS;
                break;
            case 2: actions = Actions.ADD_TASK;
                break;
            case 3: actions = Actions.REMOVE_TASK;
                break;
            case 4: actions = Actions.CHANGE_TASK;
                break;
            case 5: actions = Actions.CALENDAR;
                break;
            case 0: actions = Actions.FINISH;
        }
        process(actions);
    }

    private void backMenu() {
        try {
            Thread.sleep(1000);
            log.info("Sleep удалось.");
        }
        catch (InterruptedException e) {
            System.out.println("Ошибка. Возврат в главное меню");
            log.error("Sleep не удалось.");
        }
        finally {
            mainMenu();
        }
    }
}
