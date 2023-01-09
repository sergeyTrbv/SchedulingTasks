package Task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class YearlyTask extends Task {   //Класс ежегодной задачи

    //Конструткор
    public YearlyTask(String title, String description, LocalDateTime taskDateTime, TaskType taskType) {
        super(title, description, taskDateTime, taskType);
    }

    //Методы
    @Override
    public boolean appearsIn(LocalDate localDate) {   //Метод сравнения даты задач
        LocalDate taskDate = this.getTaskDateTime().toLocalDate();
        return localDate.equals(taskDate) ||                                       // Метод сравнивает дату, вводимую пользователем, с датой задачи, которая есть "в списке" ИЛИ
                (localDate.isAfter(taskDate) &&                                    // если есть задача с датой после указанной пользователем И
                        localDate.getDayOfMonth() == taskDate.getDayOfMonth() &&   // есть задача с таким же днём И
                        localDate.getMonth().equals(taskDate.getMonth()));         // есть задача с таким же месяцем (то есть каждый год)
    }

    @Override
    public Repeatability getRepeatabilityType() {   //Метод возвращает инф. что задача ежегодная
        return Repeatability.YEARLY;
    }
}


