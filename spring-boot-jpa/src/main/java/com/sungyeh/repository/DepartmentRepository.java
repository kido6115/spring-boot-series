package com.sungyeh.repository;

import com.sungyeh.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    Department findByNo(String no);
}
