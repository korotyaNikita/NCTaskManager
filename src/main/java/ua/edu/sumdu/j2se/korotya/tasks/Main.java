package ua.edu.sumdu.j2se.korotya.tasks;

import ua.edu.sumdu.j2se.korotya.tasks.controller.Actions;
import ua.edu.sumdu.j2se.korotya.tasks.controller.Controller;
import ua.edu.sumdu.j2se.korotya.tasks.controller.MenuController;
import ua.edu.sumdu.j2se.korotya.tasks.model.*;
import ua.edu.sumdu.j2se.korotya.tasks.view.*;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		AbstractTaskList taskList = new ArrayTaskList();
		TaskIO.readBinary(taskList, new File("tasks.txt"));
		View view = new View();
		Controller controller = new MenuController(view, taskList);
		controller.process(Actions.MAIN_MENU);
	}
}
