package ua.edu.sumdu.j2se.korotya.tasks.view;

public class Notification {
    public void display(long sec, String title) {
        System.out.println("Задача " + title + " должна выполниться через" + (sec / 60) + " минут");
    }
}
