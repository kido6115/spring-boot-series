package com.sungyeh.repository;

import com.sungyeh.domain.Department;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

/**
 * DepartmentRepository
 *
 * @author sungyeh
 */
@DisplayName("Person單元測試")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class DepartmentRepositoryTest {

    @Resource
    private DepartmentRepository departmentRepository;

    @Test
    public void testFindByNo() {
        Department department = departmentRepository.findByNo("RD");
        Assertions.assertNotNull(department);
        Assertions.assertEquals(3, department.getPersons().size());
        department.getPersons().forEach(p -> System.out.println(p.getUsername()));
    }
}
