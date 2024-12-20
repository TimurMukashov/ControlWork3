import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.LoggerConfig;
import org.example.Person;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.Arrays;

public class Main {
    static Logger logger;

    public static List<Person> getPersons() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            List<Person> persons = mapper.readValue(
                    new File("persons.json"),
                    new TypeReference<>() {}
            );
            logger.log(Level.INFO, "JSON файл успешно считан");
            return persons;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка чтения JSON файла: " + e.getMessage(), e);
        }
        catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Некорректные данные в JSON файле: " + e.getMessage(), e);
        }

        return Collections.emptyList();
    }

    public static List<Person> personsOlder18(List<Person> persons) throws IOException {;
        List<Person> filteredPersons = persons.stream().filter(person -> person.getAge() > 18).toList();

        for (int i = 0; i < filteredPersons.size(); i++)
            logger.log(Level.INFO, filteredPersons.get(i).getName());

        return filteredPersons;
    }

    public static void writePersons(List<Person> filteredUsers) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            objectMapper.writeValue(
                    new File("personsChanged.json"),
                    filteredUsers
            );
            logger.log(Level.INFO, "Данные успешно записаны в файл personsChanged.json");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка записи в файл personsChanged.json: " + e.getMessage(), e);
        }
    }

    public static Double averageAgeOfPersons(List<Person> persons) {
        if (persons.isEmpty())
            return 0.0;

        double sum = persons.stream()
                .mapToDouble(Person::getAge)
                .sum();

        return sum / persons.size();
    }

    public static List<Person> getPersonsWithLeapYear(List<Person> persons) {
        return persons.stream()
                .filter(person -> {
                    int year = person.getDateOfBirth().getYear();
                    return (year % 4 == 0) && (year % 100 != 0)  || year % 400 == 0;
                })
                .collect(Collectors.toList());
    }

    public static void bubbleSort(int [] sortArr) {
        for (int i = 0; i < sortArr.length - 1; i++)
            for (int j = 0; j < sortArr.length - i - 1; j++)
                if (sortArr[j + 1] < sortArr[j]) {
                    int swap = sortArr[j];
                    sortArr[j] = sortArr[j + 1];
                    sortArr[j + 1] = swap;
                }
    }


    public static void main(String[] args) throws IOException {
        logger = LoggerConfig.createLogger(Main.class);

        // 1. Создайте список объектов класса Person с полями name и дата рождения, email.
        // 2. Информацию о Person считаваем из json-файла. Логируйте результаты о прочтении
        List<Person> persons = getPersons();

        // 3. Используя Stream API, отфильтруйте людей старше 18 лет
        // и соберите их имена и email в новый список. (*)
        // Отфильтрованные данные записывайте также в JSON-файл.
        // Логируйте имена пользователей, которые прошли фильтр.
        List<Person> filteredPersons = personsOlder18(persons);
        writePersons(filteredPersons);

        // (*)
        List<Map<String, String>> infoOfFilteredPersons = filteredPersons
                .stream()
                .map(person -> Map.of(
                        "name", person.getName(),
                        "email", person.getEmail()
                ))
                .toList();
        System.out.println("Имена и почты отфильтрованных людей: ");
        for (Map<String, String> map : infoOfFilteredPersons)
            System.out.println(map.get("name") + " - " + map.get("email"));

        // 4. Определить средний возраст людей.
        Double averageAge = averageAgeOfPersons(persons);
        System.out.println("Средний возраст людей: " + averageAge);

        // 5. Выведите список людей, которые родились в высокосный год.
        List<Person> personsWithLeapYear = getPersonsWithLeapYear(persons);
        System.out.println("Список людей, которые родились в высокосный год: ");
        for (Person person : personsWithLeapYear)
            System.out.println(person.getName() + " - " + person.getDateOfBirth().getYear());

        // 6. Используя Stream API, сгруппируйте людей по возрастным группам (ребенок, молодежь, пожилой).
        List<Person> childs = persons.stream().filter(person -> person.getAge() < 18).toList();
        List<Person> youngs = persons.stream()
                .filter(person -> person.getAge() > 18)
                .filter(person -> person.getAge() < 65).toList();
        List<Person> olds = persons.stream().filter(person -> person.getAge() > 65).toList();

        // 7.
        List<Employee> employees = Arrays.asList(
                new Employee("Артем", "Manager", 20),
                new Employee("Данат", "Developer", 21),
                new Employee("Иван", "Developer", 19),
                new Employee("Тимур", "HR", 25),
                new Employee("Элоиза", "Manager", 99)
        );



        // 9. Реализуйте метод, который выполняет длительную операцию (например, сортировку большого массива).
        // Измерьте время выполнения и залогируйте его с помощью логгера.
        int[] largeArray = new int[100000];

        for (int i = 0; i < largeArray.length; i++)
            largeArray[i] = (int) (Math.random() * 100);

        long startTime = System.nanoTime();

        bubbleSort(largeArray);

        long endTime = System.nanoTime();
        logger.log(Level.INFO, "Время выполнения сортировки массива: " + (endTime - startTime)/1_000_000_000 + " сек.");
    }

}