package Task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DailyTask extends Task {   //Класс ежедневной задачи

    //Конструктор
    public DailyTask(String title, String description, LocalDateTime taskDateTime, TaskType taskType) {
        super(title, description, taskDateTime, taskType);
    }

    //Методы
    @Override
    public boolean appearsIn(LocalDate localDate) {   //Метод сравнения даты задач
        LocalDate taskDate = this.getTaskDateTime().toLocalDate();
        //Присваиваем переменной дату выполнения первой задачи
        return localDate.equals(taskDate) || localDate.isAfter(taskDate);
        //Метод сравнивает дату, вводимую пользователем, с датой задачи, которая есть "в списке" или если есть дата после указанной, то так же возвращаем
        //localDate.isAfter - проверка "если есть дата после указанной"
    }

    @Override
    public Repeatability getRepeatabilityType() {   //Метод возвращает инф. что задача ежедневная
        return Repeatability.DAILY;
    }


}
