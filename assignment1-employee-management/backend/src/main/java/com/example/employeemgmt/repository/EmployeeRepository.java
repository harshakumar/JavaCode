package com.example.employeemgmt.repository;

import com.example.employeemgmt.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Case-insensitive partial match on department, or all employees if department is null/blank —
    // handled at the service layer, this covers the filtered case.
    Page<Employee> findByDepartmentIgnoreCaseContaining(String department, Pageable pageable);

    // Case-insensitive partial match on name, used for the search box.
    Page<Employee> findByNameIgnoreCaseContaining(String name, Pageable pageable);

    Optional<Employee> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}
