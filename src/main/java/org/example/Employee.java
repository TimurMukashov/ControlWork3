import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

class Employee {
    private String name;
    private String position;
    private int age;

    // Конструктор
    public Employee(String name, String position, int age) {
        this.name = name;
        this.position = position;
        this.age = age;
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public int getAge() {
        return age;
    }

    // Переопределение toString для удобного вывода
    @Override
    public String toString() {
        return "Employee{name='" + name + "', position='" + position + "', age=" + age + "}";
    }
}

