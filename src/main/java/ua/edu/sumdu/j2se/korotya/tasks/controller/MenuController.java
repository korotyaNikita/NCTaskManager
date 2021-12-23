package ua.edu.sumdu.j2se.korotya.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.korotya.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.korotya.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.korotya.tasks.view.View;

import java.io.File;

public class MenuController extends Controller {
    private static final Logger log = Logger.getLogger(MenuController.class);

    public MenuController(View view, AbstractTaskList taskList) {
        super(view, taskList);
    }

    @Override
    public void process(Actions actions) {
        switch (actions) {
            case MAIN_MENU: mainMenu();
                break;
            case SHOW_TASKS: showTasks();
                break;
            case ADD_TASK: addTask();
                break;
            case REMOVE_TASK: removeTask();
                break;
            case CHANGE_TASK: changeTask();
                break;
            case CALENDAR: calendar();
                break;
            case FINISH:
                TaskIO.writeBinary(taskList, new File("tasks.txt"));
                System.exit(0);
                break;
        }
    }
}
