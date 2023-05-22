package com.sungyeh.repository;

import com.sungyeh.domain.Department;

/**
 * DepartmentRepository
 *
 * @author sungyeh
 */
public interface DepartmentRepository extends BaseRepository<Department, String> {

    Department findByNo(String no);
}
