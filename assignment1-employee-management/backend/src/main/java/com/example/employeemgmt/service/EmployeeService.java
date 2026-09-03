package com.example.employeemgmt.service;

import com.example.employeemgmt.dto.EmployeeRequest;
import com.example.employeemgmt.dto.EmployeeResponse;
import com.example.employeemgmt.dto.PagedResponse;
import com.example.employeemgmt.entity.Employee;
import com.example.employeemgmt.exception.DuplicateEmailException;
import com.example.employeemgmt.exception.ResourceNotFoundException;
import com.example.employeemgmt.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public EmployeeResponse create(EmployeeRequest request) {
        if (employeeRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new DuplicateEmailException(
                    "An employee with email '" + request.getEmail() + "' already exists");
        }
        Employee employee = Employee.builder()
                .name(request.getName())
                .email(request.getEmail())
                .department(request.getDepartment())
                .designation(request.getDesignation())
                .salary(request.getSalary())
                .joiningDate(request.getJoiningDate())
                .build();

        return toResponse(employeeRepository.save(employee));
    }

    @Transactional(readOnly = true)
    public EmployeeResponse getById(Long id) {
        return toResponse(findEntityOrThrow(id));
    }

    @Transactional(readOnly = true)
    public PagedResponse<EmployeeResponse> list(String department, String search,
                                                 int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> result;
        if (department != null && !department.isBlank()) {
            result = employeeRepository.findByDepartmentIgnoreCaseContaining(department, pageable);
        } else if (search != null && !search.isBlank()) {
            result = employeeRepository.findByNameIgnoreCaseContaining(search, pageable);
        } else {
            result = employeeRepository.findAll(pageable);
        }

        List<EmployeeResponse> content = result.getContent().stream()
                .map(this::toResponse)
                .toList();

        return new PagedResponse<>(
                content, result.getNumber(), result.getSize(),
                result.getTotalElements(), result.getTotalPages(), result.isLast());
    }

    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee existing = findEntityOrThrow(id);

        // If the email is changing, make sure it doesn't collide with a different employee.
        if (!existing.getEmail().equalsIgnoreCase(request.getEmail())
                && employeeRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new DuplicateEmailException(
                    "An employee with email '" + request.getEmail() + "' already exists");
        }

        existing.setName(request.getName());
        existing.setEmail(request.getEmail());
        existing.setDepartment(request.getDepartment());
        existing.setDesignation(request.getDesignation());
        existing.setSalary(request.getSalary());
        existing.setJoiningDate(request.getJoiningDate());

        return toResponse(employeeRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        Employee existing = findEntityOrThrow(id);
        employeeRepository.delete(existing);
    }

    private Employee findEntityOrThrow(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    private EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .designation(employee.getDesignation())
                .salary(employee.getSalary())
                .joiningDate(employee.getJoiningDate())
                .build();
    }
}
