package ua.edu.sumdu.j2se.korotya.tasks.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Task implements Cloneable, Serializable {
    private String title;
    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;
    private int interval;
    private boolean active;

    public Task() {
        title = "No title";
    }

    public Task(String title, LocalDateTime time) {
        if (time == null)
            throw new IllegalArgumentException();

        this.title = title;
        this.time = time;
        start = time;
        end = start;
        interval = 0;
    }

    public Task(String title, LocalDateTime start, LocalDateTime end, int interval)  {
        if (start == null || end == null || interval < 0 || start.isAfter(end))
            throw new IllegalArgumentException();

        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        time = start;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;

        if (isRepeated()) {
            return title.equals(task.title)
                    && start.isEqual(task.start)
                    && end.isEqual(task.end)
                    && interval == task.interval
                    && active == task.active;
        } else {
            return title.equals(task.title) && time.isEqual(task.time) && active == task.active;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, time, interval, active);
    }

    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }

    @Override
    public String toString() {
        if (!isRepeated()) {
            return "title='" + title + '\''
                    + ", time=" + time
                    + ", active=" + active;
        }
        else {
            return "title='" + title + '\''
                    + ", start=" + start
                    + ", end=" + end
                    + ", interval=" + interval + " seconds"
                    + ", active=" + active;
        }
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
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Метод для зміни часу виконання задачі
     * У разі, якщо задача повторювалась, вона стає такою,
     * що не повторюється
     */
    public void setTime(LocalDateTime time) {
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
    public LocalDateTime getStartTime() {
        return start;
    }

    /**
     * Метод для зчитування часу закінчення виконання для задач, що повторюються
     * @return end у разі, якщо задача не повторюється метод повертає час
     * виконання задачі
     */
    public LocalDateTime getEndTime() {
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
    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
        if (this.interval == 0)
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
     * метод повертає null
     */
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if (current == null)
            throw new IllegalArgumentException();

        if (!isActive()) return null;

        if (isRepeated()) {
            ChronoUnit seconds = ChronoUnit.SECONDS;
            long countOfInterval = seconds.between(start, end) / interval;

            if(current.compareTo(start.plusSeconds(interval * countOfInterval)) >= 0)
                return null;
            else {
                long a = current.compareTo(start) >= 0 ? seconds.between(start, current) : -interval;
                int intervalNumber = (int)(((double)a / interval) + 1.0);
                return start.plusSeconds((long) interval * intervalNumber);
            }
        }
        else {
            if (current.compareTo(time) >= 0)
                return null;
            else
                return time;
        }
    }
}
