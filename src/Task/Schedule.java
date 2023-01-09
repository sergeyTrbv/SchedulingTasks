package Task;

import java.time.LocalDate;
import java.util.*;

public class Schedule {   //Класс "График"

    private final Map<Integer, Task> tasks = new HashMap<>();   //Ассоциативный массив , где ключ-Integer, а значение - задача

    //Методы
    public void addTask(Task task) {                           //Метод добавление задачи
        this.tasks.put(task.getId(), task);                    //В мапу добавляем (айди задачи, саму задачу)
    }

    public Collection<Task> getAllTasks() {                    //Метод, который выводит все задачи
        return this.tasks.values();
    }

    public Collection<Task> getTaskForDay(LocalDate date) {   //Метод возвращающий коллекцию задач на сегодняшний день
        TreeSet<Task> taskForDate = new TreeSet<>();          //Список задач на определенную дату. TreeSet - множество, в котором задачи будут заранее отсортированы
        for (Task task : tasks.values()) {                    //Цикл перебирает задачи
            if (task.appearsIn(date)) {                       //Если задача есть на определенную дату, то
                taskForDate.add(task);                        //добавляем эту задачу в список "taskForDate"
            }
        }
        return taskForDate;  //Возвращаем задачу на определенную дату
    }

    public void removeTask(int id) throws TaskNotFoundException {  //Метод удаления задачи по id
        if (this.tasks.containsKey(id)) {                          //Если задача содержит ключ (id)
            this.tasks.remove(id);                                 //Удаляем задачу
        } else {                                                   //Иначе выдаём ошибку
            throw new TaskNotFoundException();
        }
    }
}
