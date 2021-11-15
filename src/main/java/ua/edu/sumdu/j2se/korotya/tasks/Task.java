package ua.edu.sumdu.j2se.korotya.tasks;

public class Task {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean active = false;

    public Task(String title, int time) throws IllegalArgumentException {
        if (time < 0)
            throw new IllegalArgumentException();

        this.title = title;
        this.time = time;
        start = time;
        end = start;
        interval = 0;
    }

    public Task(String title, int start, int end, int interval) throws IllegalArgumentException {
        if (start < 0 || end < 0 || interval < 0)
            throw new IllegalArgumentException();

        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        time = start;
    }

    /**
     * Метод для зчитування назви задачі
     */
    public String getTitle() {
        return title;
    }

    /**
     * Метод для встановлення назви задачі
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Метод для зчитування стану задачі
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Метод для встановлення стану задачі
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Метод для зчитування часу виконання задачі
     * @return time у разі, якщо задача повторюється
     * метод повертає час початку повторення
     */
    public int getTime() {
        return time;
    }

    /**
     * Метод для зміни часу виконання задачі
     * У разі, якщо задача повторювалась, вона стає такою,
     * що не повторюється
     */
    public void setTime(int time) {
        this.time = time;

        if(interval != 0) {
            start = time;
            end = start;
            interval = 0;
        }
    }

    /**
     * Метод для зчитування часу початку виконання для задач, що повторюються
     * @return start у разі, якщо задача не повторюється метод повертає час
     * виконання задачі
     */
    public int getStartTime() {
        return start;
    }

    /**
     * Метод для зчитування часу закінчення виконання для задач, що повторюються
     * @return end у разі, якщо задача не повторюється метод повертає час
     * виконання задачі
     */
    public int getEndTime() {
        return end;
    }

    /**
     * Метод для зчитування інтервалу повторення задачі
     * @return interval у разі, якщо задача не повторюється
     * метод повертає 0
     */
    public int getRepeatInterval() {
        return interval;
    }

    /**
     * Метод для встановлення часу виконання для задач, що повторюються
     * У разі, якщо задача не повторювалась, вона стає такою, що повторюється
     * @param start час початку виконання
     * @param end час закінчення виконання
     * @param interval інтервал повторення
     */
    public void setTime(int start, int end, int interval) {
        if(this.interval == 0)
            time = start;

        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    /**
     * Метод для перевірки задачі на повторюваність
     * @return true якщо інтервал повторюваності не дорівнює 0,
     * false - якщо дорівнює 0
     */
    public boolean isRepeated() {
        return interval != 0;
    }

    /**
     * Метод повертає час наступного виконання задачі після вказаного часу
     * Якщо після вказаного часу задача не виконується або задача не активна,
     * метод повертає -1
     */
    public int nextTimeAfter(int current) {
        if(!isActive()) return -1;

        if(isRepeated()) {
            int countOfInterval = (end - start) / interval;

            if(current >= start + interval * countOfInterval)
                return -1;
            else {
                int intervalNumber = (int)(((double)(current - start) / interval) + 1.0);
                return start + interval * intervalNumber;
            }
        }
        else {
            if (current >= time)
                return -1;
            else
                return time;
        }
    }
}
