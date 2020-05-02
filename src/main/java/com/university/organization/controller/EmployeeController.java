package com.university.organization.controller;

import com.university.organization.model.AverageSalary;
import com.university.organization.model.Employee;
import com.university.organization.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/")
    public String getAllTutorials(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String group,
            @RequestParam(required = false) String bossName,
            Model model
    ) {
        List<Employee> employees = new ArrayList<>();
        try {
            if (name == null && group == null && bossName == null) {
                employees = employeeRepository.findAll();
            } else {
                String queryName = name == null ? "%" : "%" + name + "%";
                String queryGroup = group == null ? "%" : "%" + group + "%";

                if (bossName == null || bossName.isEmpty()) {
                    employees = employeeRepository.searchEmployeesWithoutBoss(queryName, queryGroup);
                } else {
                    String queryBossName = "%" + bossName + "%";
                    employees = employeeRepository.searchEmployeesWithBoss(queryName, queryGroup, queryBossName);
                }
            }

        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("name", name);
        model.addAttribute("group", group);
        model.addAttribute("bossName", bossName);
        model.addAttribute("employees", employees);
        return "home";
    }

    @GetMapping("/new")
    public String getNewEmployeePage(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "new_employee";
    }

    @PostMapping("/new")
    public ResponseEntity<Employee> createEmployee(
        @RequestBody Employee employee
    ) {
        try {
            Employee _employee = employeeRepository.save(
                    employee
            );
            return new ResponseEntity<>(_employee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable("id") long id,
            @RequestBody Employee newEmployee) {
        Optional<Employee> employeeData = employeeRepository.findById(id);

        if (employeeData.isPresent()) {
            Employee employee = employeeData.get();

            employee.setName(newEmployee.getName());
            employee.setBirthdate(newEmployee.getBirthdate());
            employee.setBoss(newEmployee.getBoss());
            employee.setGroup(newEmployee.getGroup());
            employee.setHireDate(newEmployee.getHireDate());
            employee.setPhone(newEmployee.getPhone());
            employee.setPosition(newEmployee.getPosition());
            employee.setSalary(newEmployee.getSalary());

            return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/edit")
    public String getUpdatePage(
            @PathVariable("id") long id,
            Model model
    ) {
        Optional<Employee> employeeData = employeeRepository.findById(id);

        if (employeeData.isPresent()) {

            model.addAttribute("employee", employeeData.get());
        } else {

        }
        return "edit_employee";
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> deleteEmployee(
            @PathVariable("id") long id
    ) {
        Optional<Employee> employeeData = employeeRepository.findById(id);

        if (employeeData.isPresent()) {
            Employee employee = employeeData.get();
            // If the employee is someone's boss
            List<Employee> workers = employeeRepository.findByBoss(employee);
            if (workers.size() > 0) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            employeeRepository.delete(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/statistics")
    public String getStatisticsPages(Model model) {
        // Calculate average salary
        float avgSalary = employeeRepository.getAverageSalary();
        model.addAttribute("averageSalary", avgSalary);

        // Calculate average salaries by groups
        List<AverageSalary> groupsSalaries = employeeRepository.getGroupsAverageSalaries();
        model.addAttribute("groupsSalaries", groupsSalaries);

        // Get top salaries
        List<Employee> mostPaidEmployees = employeeRepository.findTop10ByOrderBySalaryDesc();
        model.addAttribute("mostPaid", mostPaidEmployees);

        // Get top years

        List<Employee> longestWorkingEmployees = employeeRepository.findTop10ByOrderByHireDate();
        model.addAttribute("mostYears", longestWorkingEmployees);


        return "statistics";
    }
}
