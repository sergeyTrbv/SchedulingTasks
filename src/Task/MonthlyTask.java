package Task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MonthlyTask extends Task {   //Класс ежемесячной задачи

    //Конструткор
    public MonthlyTask(String title, String description, LocalDateTime taskDateTime, TaskType taskType) {
        super(title, description, taskDateTime, taskType);
    }

    //Методы
    @Override
    public boolean appearsIn(LocalDate localDate) {   //Метод сравнения даты задач
        LocalDate taskDate = this.getTaskDateTime().toLocalDate();
        return localDate.equals(taskDate) ||
                (localDate.isAfter(taskDate)) &&
                        (localDate.isAfter(taskDate)) && localDate.getDayOfMonth() == localDate.getDayOfMonth();
        //Метод сравнивает дату, вводимую пользователем, с датой задачи, которая есть "в списке" или если есть задача с датой после указанной и ...
        //... и день месяца преданный пользователем равен дню месяца задачи
    }


    @Override
    public Repeatability getRepeatabilityType() {   //Метод возвращает инф. что задача ежемесячная
        return Repeatability.MONTHLY;
    }


}
