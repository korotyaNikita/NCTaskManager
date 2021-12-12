package ua.edu.sumdu.j2se.korotya.tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.time.LocalDateTime;

public class TaskIO {
    public static void write (AbstractTaskList tasks, OutputStream out) {
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeInt(tasks.size());
            for (Task task: tasks) {
                oos.writeInt(task.getTitle().length());
                oos.writeChars(task.getTitle());
                oos.writeBoolean(task.isActive());
                oos.writeInt(task.getRepeatInterval());

                if (task.isRepeated()) {
                    oos.writeObject(task.getStartTime());
                    oos.writeObject(task.getEndTime());
                }
                else {
                    oos.writeObject(task.getTime());
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read (AbstractTaskList tasks, InputStream in) {
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            int size = ois.readInt();
            for (int i = 0; i < size; i++) {
                int titleLength = ois.readInt();
                char[] chars = new char[titleLength];

                for (int j = 0; j < titleLength; j++) {
                    chars[j] = ois.readChar();
                }

                boolean active = ois.readBoolean();
                int interval = ois.readInt();
                LocalDateTime time = (LocalDateTime) ois.readObject();

                Task task;
                String title = String.valueOf(chars);
                if (interval == 0)
                    task = new Task(title, time);
                else {
                    LocalDateTime endTime = (LocalDateTime) ois.readObject();
                    task = new Task(title, time, endTime, interval);
                }

                task.setActive(active);
                tasks.add(task);
            }
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void writeBinary(AbstractTaskList tasks, File file) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            write(tasks, fileOutputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readBinary (AbstractTaskList tasks, File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            read(tasks, fileInputStream);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void write (AbstractTaskList tasks, Writer out) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        try (FileWriter fileWriter = (FileWriter) out) {
            fileWriter.write(gson.toJson(tasks.getStream().toArray(Task[]::new)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read (AbstractTaskList tasks, Reader in){
        Gson gson = new Gson();

        try (FileReader fileReader = (FileReader) in) {
            for (Task task : gson.fromJson(fileReader, Task[].class)) {
                tasks.add(task);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeText (AbstractTaskList tasks, File file){
        try (FileWriter fileWriter = new FileWriter(file)) {
            write(tasks, fileWriter);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readText (AbstractTaskList tasks, File file) {
        try (FileReader fileReader = new FileReader(file)) {
            read(tasks, fileReader);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
