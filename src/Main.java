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

    //Переменные
    private static final Schedule SCHEDULE = new Schedule();   //Статическая переменная "ГРАФИК"
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d.MM.yyyy");   //Дату оборачиваем в "удобный вид"
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");   //Время оборачиваем в "удобный вид"

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);     //объект класса Scanner(Используя методы к этому объекту, сможем вызвать функии ввода в коносль

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
        String title = readString("Введите название задачи: ", scanner);           //Строка title , которая получает readString в котрую передаем cообщение и сканер
        String discription = readString("Введите описание задачи:", scanner);      //Объект discription , которая получает readString в котрую передаем cообщение и сканер
        LocalDateTime taskDate = readDateTime(scanner);                                    //Объект taskDate, который получает дату
        TaskType taskType = readType(scanner);                                             //Объект taskType, котоорый получает тип задачи(Л/Р)
        Repeatability repeatability = readRepeatability(scanner);                          //Объект repeatability, который получает тип повторяемости
        Task task = switch (repeatability) {                                               //
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
                System.out.println("Выберите тип задачи:");                                  //Выводим пользователю список задач
                for (TaskType taskType : TaskType.values()) {                                //Перебираем все типы задач
                    System.out.println(taskType.ordinal() + ". " + localizeType(taskType));  /*Выводим по ordinal-значению (по нумерации) + localizeType (Метод приводящий тип задачи)
                . ordinal() - Возвращает порядковый номер этой константы перечисления, ее позицию в объявлении перечисления, где исходной константе присваивается порядковый номер нуля*/
                }
                System.out.print("Введите тип:");
                String ordinalLine = scanner.nextLine();                                    //Строка ordinalLine принимает значение вводимое пользователем;
                int ordinal = Integer.parseInt(ordinalLine);                                //Конвертируем строку ordinalLine в целое число int; parseInt преобразует строку в число
                return TaskType.values()[ordinal];                                          //Возвращаем...
            } catch (
                    NumberFormatException e) {                                             //Обрабатываем исключение если пользователь ввел неправильный идентификатор задачи
                System.out.println("Введён неверный номер типа задачи");
            } catch (ArrayIndexOutOfBoundsException e) {                                    //Обрабатываем исключение
                System.out.println("Тип задачи не найден");
            }
        }
    }

    private static Repeatability readRepeatability(Scanner scanner) {                                          //МЕТОД "ПРОЧИТАТЬ ПОВТОРЯЕМОСТЬ ЗАДАЧИ"
        while (true) {
            try {
                System.out.print("Выберите повторяемость задачи:");                                          //Выводим пользователю список повторяемости задач
                for (Repeatability repeatability : Repeatability.values()) {                                   //Перебираем все типы повторяемости задач
                    System.out.println(repeatability.ordinal() + ". " + localizeRepeatability(repeatability)); //Выводим по ordinal-значению (по нумерации) + localizeRepeatability (Метод приводящий тип повторяемости задачи)
                }
                System.out.println("Введите повторяемость:");
                String ordinalLine = scanner.nextLine();                                    //Строка ordinalLine принимает значение вводимое пользователем;
                int ordinal = Integer.parseInt(ordinalLine);                                //Конвертируем строку ordinalLine в целое число int; parseInt преобразует строку в число
                return Repeatability.values()[ordinal];                                     //Возвращаем...
            } catch (
                    NumberFormatException e) {                                             //Обрабатываем исключение если пользователь ввел неправильный идентификатор задачи
                System.out.println("Введён неверный номер типа задачи");
            } catch (ArrayIndexOutOfBoundsException e) {                                    //Обрабатываем исключение
                System.out.println("Тип задачи не найден");
            }
        }
    }

    private static LocalDateTime readDateTime(Scanner scanner) {                 //МЕТОД, СЧИТЫВАЮЩИЙ ВРЕМЯ ЗАДАЧИ
        LocalDate localDate = readDate(scanner);                                 //Объект localDate ссылается на метод "Прочитать дату" и присваеивает значение пользователя
        LocalTime localTime = readTime(scanner);                                 //Объект localTime ссылается на метод "Прочитать время" и присваеивает значение пользователя
        return localDate.atTime(localTime);                                      //Возвращаем объединенные 2 метода с датой и временем

    }

    private static String readString(String message, Scanner scanner) {           //МЕТОД, СЧИТЫВАЮЩИЙ СТРОКУ ПОЛЬЗОВАТЕЛЯ
        while (true) {
            System.out.println(message);
            String readString = scanner.nextLine();                               //Строке readString присваиваем значение,которое ввёл пользователь
            if (readString == null || readString.isEmpty()) {
                System.out.println("Введено пустое значение");
            } else {
                return readString;
            }
        }
    }

    private static void removeTask(Scanner scanner) {                             //МЕТОД "УДАЛИТЬ ЗАДАЧУ"
        System.out.println("Все задачи:");
        for (Task task : SCHEDULE.getAllTasks()) {                                //Итерируемся по задачам, которые есть в списке
            System.out.printf("%d. %s [%s](%s)%n",                                //"%идентификатор задачи. %название задачи [%тип задачи Л/Р](%повторяемость)%n"
                    task.getId(),                                                 //Выводим id задачи
                    task.getTitle(),                                              //Выводим название задачи
                    localizeType(task.getTaskType()),                             //Выводим тип (Л/Р) через метод localizeType
                    localizeRepeatability(task.getRepeatabilityType()));          //Выводим тип повторяемости через метод localizeRepeatability

        }
        System.out.println(SCHEDULE.getAllTasks());
        while (true) {
            try {
                System.out.println("Выберите задачу для удаления:");              //Выводим пользователю список задач
                String idLine = scanner.nextLine();                               /*Строка idLine принимает значение вводимое пользователем;
                NextLine () в Java применяется для класса Scanner. Этот метод нужен для получения и чтения данных, написанных пользователем*/
                int id = Integer.parseInt(idLine);                                //Конвертируем строку idLine в целое число int; parseInt преобразует строку в число
                SCHEDULE.removeTask(id);                                          //Удаляем задачу
                break;
            } catch (NumberFormatException e) {                                   //Обрабатываем исключение
                System.out.println("Введён неверный id задачи");
            } catch (TaskNotFoundException e) {                                   //Обрабатываем исключение
                System.out.println("Задача для удаления не найдена");
            }
        }
    }

    public static void printTaskForDate(Scanner scanner) {                                //МЕТОД, КОТОРЫЙ ВЫВОДИТ ЗАДАЧИ НА ДЕНЬ (Принимаем сканер)
        LocalDate localDate = readDate(scanner);                                          //Получаем дату пользователя
        Collection<Task> taskForDate = SCHEDULE.getTaskForDay(localDate);                 /*Получаем колелкцию задач (getTaskForDay-Метод возвращающий  коллекцию задач...
                                                                                          ...на сегодняшний день; в него передаем сегодняшний день)*/
        System.out.println("Задачи на " + localDate.format(DATE_FORMAT));
        for (Task task : taskForDate) {
            System.out.printf("[%s] %s: %s (%s) %n",                                      // "[%тип задачи] %название задачи: %время в которое задача выполняется (%описание задачи) %перенос строки"
                    localizeType(task.getTaskType()),                                     //Тип задачи
                    task.getTitle(),                                                      //Название задачи
                    task.getTaskDateTime().format(TIME_FORMAT),                           //ВРЕМЯ выполнения задачи
                    task.getDescription());                                               //Описание задачи
        }

    }

    private static LocalDate readDate(Scanner scanner) {                                  //МЕТОД "ПРОЧИТАТЬ ДАТУ ПОЛЬЗОВАТЕЛЯ"
        while (true) {
            try {
                System.out.println("Введите дату задачи в формате dd.MM.yyyy: ");
                String dateLine = scanner.nextLine();                                     //Строке dateLine присваиваем значение,которое ввёл пользователь
                return LocalDate.parse(dateLine, DATE_FORMAT);                            //dateLine преобразовываем с помощью .parse() в "удобный вид" даты
                //метод parse() получает некоторую строку в качестве входных данных, "извлекает" из нее необходимую информацию и преобразует ее в объект вызывающего класса
            } catch (
                    DateTimeException a) {                                                //Ловим ошибку форматирования даты
                System.out.println("Введена дата в неверном формате");
            }
        }
    }

    private static LocalTime readTime(Scanner scanner) {                                  //МЕТОД "ПРОЧИТАТЬ ВРЕМЯ ПОЛЬЗОВАТЕЛЯ"
        while (true) {
            try {
                System.out.println("Введите время задачи в формате hh:mm: ");
                String dateLine = scanner.nextLine();                                     //Строке dateLine присваиваем значение,которое ввёл пользователь
                return LocalTime.parse(dateLine, TIME_FORMAT);                            //dateLine преобразовываем с помощью .parse() в "удобный вид" времени
            } catch (
                    DateTimeException a) {                                                //Ловим ошибку форматирования даты
                System.out.println("Введена дата в неверном формате");
            }
        }
    }

    private static String localizeType(TaskType taskType) { //Метод приводящий тип задачи

        return switch (taskType) {
            case WORK -> "Рабочая задача";
            case PERSONAL -> "Личная задача";
        };
    }

    private static String localizeRepeatability(Repeatability repeatability) {             /*Повторяемость(тип) задачи
        Принимаем класс Repeatability-"Повторяемость задачи"*/
        return switch (repeatability) {

            case SINGLE -> "Разовая";
            case DAILY -> "Ежедневная";
            case WEEKLY -> "Еженедельная";
            case MONTHLY -> "Ежемесячная";
            case YEARLY -> "Ежегодная";
        };
    }
}