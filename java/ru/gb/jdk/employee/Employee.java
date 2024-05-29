package ru.gb.jdk.employee;

/**
 * Создать справочник сотрудников
 * Необходимо:
 * Создать класс справочник сотрудников, который содержит внутри
 * коллекцию сотрудников - каждый сотрудник должен иметь следующие атрибуты:
 * Табельный номер
 * Номер телефона
 * Имя
 * Стаж
 * Добавить метод, который ищет сотрудника по стажу (может быть список)
 * Добавить метод, который возвращает номер телефона сотрудника по имени (может быть список)
 * Добавить метод, который ищет сотрудника по табельному номеру
 * Добавить метод добавления нового сотрудника в справочник
 */
public class Employee {
    //    private int serviceNumber;
    private String name;
    private String phone;
    private int experience;

    public Employee(String name, String phone, int experience) {
        this.name = name;
        this.phone = phone;
        this.experience = experience;
    }


    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getExperience() {
        return experience;
    }

    @Override
    public String toString() {
        return " name: " + name + " phone: " + phone + " experience: " + experience;
    }
}
