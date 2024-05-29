package ru.gb.jdk.employee;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Добавить метод, который ищет сотрудника по стажу (может быть список)
 * Добавить метод, который возвращает номер телефона сотрудника по имени (может быть список)
 * Добавить метод, который ищет сотрудника по табельному номеру
 * Добавить метод добавления нового сотрудника в справочник
 */

public class Employees {
    private static final Map<Integer, Employee> employees = new TreeMap<>();

    public static void main(String[] args) {
        init();
        System.out.println("Список работников:");
        System.out.println(getList(employees));

        int experience = 5;
        Map<Integer, Employee> map1 = findEmployeeByExperience(experience);
        System.out.printf("Cписок работников со стажем %d лет\n", experience);
        System.out.println(getList(map1));

        String name = "Peter";
        System.out.println("Список телефонных номеров работников по имени " + name + ":");
        System.out.println(getPhoneByName(name));

        int sn = 440067;
        System.out.println("\nПоиск работника по табельному номеру " + sn + ":");
        Employee employee = findEmployeeBySN(sn);
        System.out.println(sn + " " + employee);

        System.out.println("\nДобавление работника:");
        System.out.println(add(440088, "Kirill", "942938469", 4));

        System.out.println("\nСписок работников:");
        System.out.println(getList(employees));

    }


    private static void init() {
        employees.put(440001, new Employee("Peter", "89343443", 5));
        employees.put(440002, new Employee("Maria", "89300043", 8));
        employees.put(440014, new Employee("Vlada", "80101443", 12));
        employees.put(440012, new Employee("Peter", "89343123", 5));
        employees.put(440067, new Employee("Kirill", "81234143", 25));
        employees.put(440005, new Employee("Anna", "86765443", 14));
        employees.put(440033, new Employee("Vlada", "89319485", 1));
    }

    public static String getList(Map<Integer, Employee> map) {
        StringBuilder sb = new StringBuilder();
        map.forEach((k, v) -> sb
                .append("sn: ")
                .append(k).append(" ")
                .append(v).append("\n")
        );
        return sb.toString();
    }

    /*
     * Добавить метод, который ищет сотрудника по стажу (может быть список)
     */
    public static Map<Integer, Employee> findEmployeeByExperience(int experience) {
        return employees
                .entrySet()
                .stream()
                .filter(e -> e.getValue().getExperience() == experience)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    /*
     * Добавить метод, который возвращает номер телефона сотрудника по имени (может быть список)
     */
    private static List<String> getPhoneByName(String name) {
        return employees.values()
                .stream()
                .filter(employee -> employee.getName() == name)
                .map(Employee::getPhone)
                .collect(Collectors.toList());
    }

    /*
     * Метод ищет сотрудника по табельному номеру
     */
    private static Employee findEmployeeBySN(int sn) {
        return employees.get(sn);
    }

    /*
     * Метод добавления нового сотрудника в справочник
     */
    public static Employee add(int sn, String name, String phone, int experience) {
        Employee employee = new Employee(name, phone, experience);
        employees.put(sn, employee);
        return employee;
    }


}
