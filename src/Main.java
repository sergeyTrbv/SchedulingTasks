import Task.Schedule;
import Task.Task;
import Task.TaskType;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Scanner;

import Task.*;

public class Main {

    private static final Schedule SCHEDULE = new Schedule();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d.MM.yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //Добавляем задачи
        //SCHEDULE.addTask(new SingleTask("SingleTest", "Test", LocalDateTime.now(), TaskType.PERSONAL));
        //SCHEDULE.addTask(new DailyTask("DailyTest", "Test", LocalDateTime.now().plusHours(3), TaskType.PERSONAL));


        //Для проверки курсовой добавьте задачу, проверьте есть ли она в списке на нужный день, удалите , снова проверьте список
//        addTask(scanner);
//        printTaskForDate(scanner);
//        removeTask(scanner);
//        printTaskForDate(scanner);


    }


    private static void addTask(Scanner scanner) {                                         //МЕТОД "ДОБАВИТЬ ЗАДАЧУ"
        String title = readString("Введите название задачи: ", scanner);
        String discription = readString("Введите описание задачи:", scanner);
        LocalDateTime taskDate = readDateTime(scanner);
        TaskType taskType = readType(scanner);
        Repeatability repeatability = readRepeatability(scanner);
        Task task = switch (repeatability) {
            case SINGLE -> new SingleTask(title, discription, taskDate, taskType);
            case DAILY -> new DailyTask(title, discription, taskDate, taskType);
            case WEEKLY -> new WeeklyTask(title, discription, taskDate, taskType);
            case MONTHLY -> new MonthlyTask(title, discription, taskDate, taskType);
            case YEARLY -> new YearlyTask(title, discription, taskDate, taskType);
        };
        SCHEDULE.addTask(task);   //Добавляем задачу в мапу
        System.out.println("Задача " + title + " добавлена!");
    }

    private static TaskType readType(Scanner scanner) {                                      //МЕТОД "ПРОЧИТАТЬ ТИП ЗАДАЧИ"
        while (true) {
            try {
                System.out.println("Выберите тип задачи:");
                for (TaskType taskType : TaskType.values()) {
                    System.out.println(taskType.ordinal() + ". " + localizeType(taskType));
                }
                System.out.print("Введите тип:");
                String ordinalLine = scanner.nextLine();
                int ordinal = Integer.parseInt(ordinalLine);
                return TaskType.values()[ordinal];
            } catch (
                    NumberFormatException e) {
                System.out.println("Введён неверный номер типа задачи");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Тип задачи не найден");
            }
        }
    }

    private static Repeatability readRepeatability(Scanner scanner) {                                          //МЕТОД "ПРОЧИТАТЬ ПОВТОРЯЕМОСТЬ ЗАДАЧИ"
        while (true) {
            try {
                System.out.print("Выберите повторяемость задачи:");
                for (Repeatability repeatability : Repeatability.values()) {
                    System.out.println(repeatability.ordinal() + ". " + localizeRepeatability(repeatability));
                }
                System.out.println("Введите повторяемость:");
                String ordinalLine = scanner.nextLine();
                int ordinal = Integer.parseInt(ordinalLine);
                return Repeatability.values()[ordinal];
            } catch (
                    NumberFormatException e) {
                System.out.println("Введён неверный номер типа задачи");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Тип задачи не найден");
            }
        }
    }

    private static LocalDateTime readDateTime(Scanner scanner) {                 //МЕТОД, СЧИТЫВАЮЩИЙ ВРЕМЯ ЗАДАЧИ
        LocalDate localDate = readDate(scanner);
        LocalTime localTime = readTime(scanner);
        return localDate.atTime(localTime);

    }

    private static String readString(String message, Scanner scanner) {           //МЕТОД, СЧИТЫВАЮЩИЙ СТРОКУ ПОЛЬЗОВАТЕЛЯ
        while (true) {
            System.out.println(message);
            String readString = scanner.nextLine();
            if (readString == null || readString.isEmpty()) {
                System.out.println("Введено пустое значение");
            } else {
                return readString;
            }
        }
    }

    private static void removeTask(Scanner scanner) {                             //МЕТОД "УДАЛИТЬ ЗАДАЧУ"
        System.out.println("Все задачи:");
        for (Task task : SCHEDULE.getAllTasks()) {
            System.out.printf("%d. %s [%s](%s)%n",
                    task.getId(),
                    task.getTitle(),
                    localizeType(task.getTaskType()),
                    localizeRepeatability(task.getRepeatabilityType()));

        }
        System.out.println(SCHEDULE.getAllTasks());
        while (true) {
            try {
                System.out.println("Выберите задачу для удаления:");
                String idLine = scanner.nextLine();
                int id = Integer.parseInt(idLine);
                SCHEDULE.removeTask(id);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введён неверный id задачи");
            } catch (TaskNotFoundException e) {
                System.out.println("Задача для удаления не найдена");
            }
        }
    }

    public static void printTaskForDate(Scanner scanner) {                                //МЕТОД, КОТОРЫЙ ВЫВОДИТ ЗАДАЧИ НА ДЕНЬ
        LocalDate localDate = readDate(scanner);
        Collection<Task> taskForDate = SCHEDULE.getTaskForDay(localDate);
        System.out.println("Задачи на " + localDate.format(DATE_FORMAT));
        for (Task task : taskForDate) {
            System.out.printf("[%s] %s: %s (%s) %n",
                    localizeType(task.getTaskType()),
                    task.getTitle(),
                    task.getTaskDateTime().format(TIME_FORMAT),
                    task.getDescription());
        }

    }

    private static LocalDate readDate(Scanner scanner) {                                  //МЕТОД "ПРОЧИТАТЬ ДАТУ ПОЛЬЗОВАТЕЛЯ"
        while (true) {
            try {
                System.out.println("Введите дату задачи в формате dd.MM.yyyy: ");
                String dateLine = scanner.nextLine();
                return LocalDate.parse(dateLine, DATE_FORMAT);
            } catch (
                    DateTimeException a) {
                System.out.println("Введена дата в неверном формате");
            }
        }
    }

    private static LocalTime readTime(Scanner scanner) {                                  //МЕТОД "ПРОЧИТАТЬ ВРЕМЯ ПОЛЬЗОВАТЕЛЯ"
        while (true) {
            try {
                System.out.println("Введите время задачи в формате hh:mm: ");
                String dateLine = scanner.nextLine();
                return LocalTime.parse(dateLine, TIME_FORMAT);
            } catch (
                    DateTimeException a) {
                System.out.println("Введена дата в неверном формате");
            }
        }
    }

    private static String localizeType(TaskType taskType) {                               //Метод приводящий тип задачи

        return switch (taskType) {
            case WORK -> "Рабочая задача";
            case PERSONAL -> "Личная задача";
        };
    }

    private static String localizeRepeatability(Repeatability repeatability) {             //Повторяемость(тип) задачи

        return switch (repeatability) {

            case SINGLE -> "Разовая";
            case DAILY -> "Ежедневная";
            case WEEKLY -> "Еженедельная";
            case MONTHLY -> "Ежемесячная";
            case YEARLY -> "Ежегодная";
        };
    }
}