package Task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WeeklyTask extends Task {   //Класс еженедельной задачи

    //Конструктор
    public WeeklyTask(String title, String description, LocalDateTime taskDateTime, TaskType taskType) {
        super(title, description, taskDateTime, taskType);
    }

    //Методы
    @Override
    public boolean appearsIn(LocalDate localDate) {   //Метод сравнения даты задач
        LocalDate taskDate = this.getTaskDateTime().toLocalDate();
        return localDate.equals(taskDate) ||
                (localDate.isAfter(taskDate)) && localDate.getDayOfWeek().equals(taskDate.getDayOfWeek());
        //Метод сравнивает дату, вводимую пользователем, с датой задачи, которая есть "в списке" или если есть задача с датой после указанной и ...
        //... и день недели преданный пользователем равен дню недели задачи
    }

    @Override
    public Repeatability getRepeatabilityType() {   //Метод возвращает инф. что задача еженедельная
        return Repeatability.WEEKLY;
    }
}
