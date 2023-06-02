package com.sungyeh.repository;

import com.sungyeh.domain.Department;

/**
 * DepartmentRepository
 *
 * @author sungyeh
 */
public interface DepartmentRepository extends BaseRepository<Department, String> {

    /**
     * 透過代號取得單筆資料
     *
     * @param no 代號
     * @return 單筆資料
     */
    Department findByNo(String no);
}
