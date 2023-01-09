package Task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SingleTask extends Task {   //Класс однократной задачи

    //Конструтор
    public SingleTask(String title, String description, LocalDateTime taskDateTime, TaskType taskType) {
        super(title, description, taskDateTime, taskType);
    }

    //Методы
    @Override
    public boolean appearsIn(LocalDate localDate) {   //Метод сравнения даты задач
        return localDate.equals(this.getTaskDateTime().toLocalDate());
        //Метод сравнивает дату, вводимую пользователем, с датой задачи, которая есть "в списке"
    }

    @Override
    public Repeatability getRepeatabilityType() {  //Метод возвращает инф. что задача однократная
        return Repeatability.SINGLE;
    }


}
