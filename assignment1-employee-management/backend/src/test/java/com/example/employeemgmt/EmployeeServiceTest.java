package com.example.employeemgmt;

import com.example.employeemgmt.dto.EmployeeRequest;
import com.example.employeemgmt.dto.EmployeeResponse;
import com.example.employeemgmt.exception.DuplicateEmailException;
import com.example.employeemgmt.exception.ResourceNotFoundException;
import com.example.employeemgmt.repository.EmployeeRepository;
import com.example.employeemgmt.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // each test rolls back, keeping the in-memory DB clean between tests
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeRequest sampleRequest;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        sampleRequest = new EmployeeRequest();
        sampleRequest.setName("Test User");
        sampleRequest.setEmail("test.user@example.com");
        sampleRequest.setDepartment("Engineering");
        sampleRequest.setDesignation("Software Engineer");
        sampleRequest.setSalary(100000.0);
        sampleRequest.setJoiningDate(LocalDate.of(2023, 1, 1));
    }

    @Test
    void createsEmployeeSuccessfully() {
        EmployeeResponse response = employeeService.create(sampleRequest);

        assertNotNull(response.getId());
        assertEquals("Test User", response.getName());
        assertEquals("test.user@example.com", response.getEmail());
    }

    @Test
    void rejectsDuplicateEmail() {
        employeeService.create(sampleRequest);

        EmployeeRequest duplicate = new EmployeeRequest();
        duplicate.setName("Another Person");
        duplicate.setEmail("test.user@example.com"); // same email
        duplicate.setDepartment("Sales");
        duplicate.setDesignation("Rep");
        duplicate.setSalary(60000.0);
        duplicate.setJoiningDate(LocalDate.of(2024, 1, 1));

        assertThrows(DuplicateEmailException.class, () -> employeeService.create(duplicate));
    }

    @Test
    void throwsWhenEmployeeNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> employeeService.getById(999L));
    }

    @Test
    void updatesEmployeeSuccessfully() {
        EmployeeResponse created = employeeService.create(sampleRequest);

        sampleRequest.setDesignation("Senior Software Engineer");
        sampleRequest.setSalary(130000.0);
        EmployeeResponse updated = employeeService.update(created.getId(), sampleRequest);

        assertEquals("Senior Software Engineer", updated.getDesignation());
        assertEquals(130000.0, updated.getSalary());
    }

    @Test
    void deletesEmployeeSuccessfully() {
        EmployeeResponse created = employeeService.create(sampleRequest);
        employeeService.delete(created.getId());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getById(created.getId()));
    }
}
