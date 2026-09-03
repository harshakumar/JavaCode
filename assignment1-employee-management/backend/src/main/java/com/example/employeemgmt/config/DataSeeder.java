package com.example.employeemgmt.config;

import com.example.employeemgmt.entity.Employee;
import com.example.employeemgmt.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Seeds a handful of employees on startup so the API and UI have something
 * to show immediately without requiring manual data entry first.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    public DataSeeder(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) {
        if (employeeRepository.count() > 0) {
            return;
        }

        employeeRepository.save(Employee.builder()
                .name("Ananya Rao").email("ananya.rao@example.com")
                .department("Engineering").designation("Senior Software Engineer")
                .salary(145000.0).joiningDate(LocalDate.of(2019, 6, 12)).build());

        employeeRepository.save(Employee.builder()
                .name("James Whitfield").email("james.whitfield@example.com")
                .department("Engineering").designation("Frontend Developer")
                .salary(98000.0).joiningDate(LocalDate.of(2022, 1, 24)).build());

        employeeRepository.save(Employee.builder()
                .name("Priya Menon").email("priya.menon@example.com")
                .department("Human Resources").designation("HR Manager")
                .salary(112000.0).joiningDate(LocalDate.of(2018, 3, 3)).build());

        employeeRepository.save(Employee.builder()
                .name("Oliver Bennett").email("oliver.bennett@example.com")
                .department("Sales").designation("Account Executive")
                .salary(87000.0).joiningDate(LocalDate.of(2023, 9, 15)).build());

        employeeRepository.save(Employee.builder()
                .name("Sneha Kapoor").email("sneha.kapoor@example.com")
                .department("Finance").designation("Financial Analyst")
                .salary(93000.0).joiningDate(LocalDate.of(2021, 11, 8)).build());
    }
}
