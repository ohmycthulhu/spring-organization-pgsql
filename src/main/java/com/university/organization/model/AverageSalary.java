package com.university.organization.model;

public class AverageSalary {
    private String group;
    private double salary;

    public AverageSalary(
            String group,
            double salary
    ) {
        this.group = group;
        this.salary = salary;
    }

    public String getGroup() { return group; }
    public double getSalary() { return salary; }

}
