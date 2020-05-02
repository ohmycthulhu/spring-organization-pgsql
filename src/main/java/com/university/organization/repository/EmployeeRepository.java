package com.university.organization.repository;

import com.university.organization.model.AverageSalary;
import com.university.organization.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByBoss(Employee boss);

    @Query(value = "SELECT e FROM Employee e WHERE e.name LIKE ?1 and e.group LIKE ?2 ")
    List<Employee> searchEmployeesWithoutBoss(
            String name,
            String group);

    @Query(value = "SELECT e FROM Employee e WHERE e.name LIKE ?1 and e.group LIKE ?2 and e.boss.name LIKE ?3")
    List<Employee> searchEmployeesWithBoss(
            String name,
            String group,
            String bossName
            );

    @Query(value = "SELECT AVG(e.salary) FROM Employee e")
    float getAverageSalary();

    @Query(value = "SELECT new com.university.organization.model.AverageSalary(e.group, AVG(e.salary)) FROM Employee e GROUP BY e.group")
    List<AverageSalary> getGroupsAverageSalaries();

    List<Employee> findTop10ByOrderBySalaryDesc();
    List<Employee> findTop10ByOrderByHireDate();

}
