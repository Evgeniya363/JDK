package ru.gb.jdk.employee;

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

public class Service {
    private Map<Integer, Employee> employees = new TreeMap<>();

    public Map<Integer, Employee> getEmployees() {
        return employees;
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
    Map<Integer, Employee> findEmployeeByExperience(int experience) {
        return employees
                .entrySet()
                .stream()
                .filter(e -> e.getValue().getExperience() == experience)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    /*
     * Добавить метод, который возвращает номер телефона сотрудника по имени (может быть список)
     */
    List<String> getPhoneByName(String name) {
        return employees.values()
                .stream()
                .filter(employee -> employee.getName() == name)
                .map(Employee::getPhone)
                .collect(Collectors.toList());
    }

    /*
     * Метод ищет сотрудника по табельному номеру
     */
    Employee findEmployeeBySN(int sn) {
        return employees.get(sn);
    }

    /*
     * Метод добавления нового сотрудника в справочник
     */
    boolean add(int sn, String name, String phone, int experience) {
        if (employees.containsKey(sn))
            return false;
        employees.put(sn, new Employee(name, phone, experience));
        return true;
    }


}
