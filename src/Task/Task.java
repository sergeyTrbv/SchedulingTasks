package Task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Task implements Comparable<Task> {   /*Абстрактный класс "модель задачи" c интерфейсом Comparable
    Интерфейс Comparable позволяет нам определять порядок между объектами, выявляя, является ли объект больше, меньше или равным другому.*/

    private static int counter = 0;
    private final int id;   //Счётчик id
    private final String title;   //Заголовок задачи
    private final String description;   //Описание задачи
    private final LocalDateTime taskDateTime;   //Дата первого выполнения задачи
    private final TaskType taskType;   //Тип задачи

    //Конструктор
    public Task(String title, String description, LocalDateTime taskDateTime, TaskType taskType) {
        this.id = counter++;
        this.title = title;
        this.description = description;
        this.taskDateTime = taskDateTime;
        this.taskType = taskType;
    }

    //Методы
    public abstract boolean appearsIn(LocalDate localDate);   //Метод для повторяющихся задач, показывающий их для определенной даты

    public abstract Repeatability getRepeatabilityType();     //Метод, котоый говорит о типе повторяемости задачи


    public int compareTo(Task otherTask) {                     //Метод сравнения (Нужен для сортировки задач)(otherTask - другая задача)
        if (otherTask == null) {                               //Если другая задача равна null, то "наша" задача выше в списке, чем другая :)
            return 1;
        }
        return this.taskDateTime.toLocalTime().compareTo(otherTask.taskDateTime.toLocalTime());  //Сравниваем время
    }


    //Геттеры
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTaskDateTime() {
        return taskDateTime;
    }

    public TaskType getTaskType() {
        return taskType;
    }

}
