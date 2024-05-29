package ru.gb.jdk.employee;

import java.util.Map;

import static ru.gb.jdk.employee.Service.*;

public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        Map<Integer, Employee> employees = service.getEmployees();
        init(employees);

        System.out.println("Список работников:");
        System.out.println(getList(employees));

        int experience = 5;
        Map<Integer, Employee> map1 = service.findEmployeeByExperience(experience);
        System.out.printf("Cписок работников со стажем %d лет\n", experience);
        System.out.println(getList(map1));

        String name = "Peter";
        System.out.println("Список телефонных номеров работников по имени " + name + ":");
        System.out.println(service.getPhoneByName(name));

        int sn = 440067;
        System.out.println("\nПоиск работника по табельному номеру " + sn + ":");
        Employee employee = service.findEmployeeBySN(sn);
        System.out.println(sn + " " + employee);

        System.out.println("\nДобавление работника:");
        if (service.add(440088, "Kirill", "942938469", 4))
            System.out.printf("Добавлено: %d %s\n", sn, employees.get(sn));
        else
            System.out.printf("Работник с табельным номером %d уже существует\n", sn);

        System.out.println("\nДобавление работника:");
        if (service.add(440067, "Kirill", "942938469", 4))
            System.out.printf("Добавлено: %d %s\n", sn, employees.get(sn));
        else
            System.out.printf("Работник с табельным номеном %d уже существует\n", sn);

        System.out.println("\nСписок работников:");
        System.out.println(getList(employees));

    }

    private static void init(Map<Integer, Employee> employees) {
        employees.put(440001, new Employee("Peter", "89343443", 5));
        employees.put(440002, new Employee("Maria", "89300043", 8));
        employees.put(440014, new Employee("Vlada", "80101443", 12));
        employees.put(440012, new Employee("Peter", "89343123", 5));
        employees.put(440067, new Employee("Kirill", "81234143", 25));
        employees.put(440005, new Employee("Anna", "86765443", 14));
        employees.put(440033, new Employee("Vlada", "89319485", 1));
    }
}
