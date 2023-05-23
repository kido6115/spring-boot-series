package com.sungyeh.web.rest;

import com.sungyeh.bean.swagger.Department;
import com.sungyeh.repository.DepartmentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * DepartmentController
 *
 * @author sungyeh
 */
@RestController
@RequestMapping("/rest/department")
@Tag(name = "部門")
public class DepartmentController {
    /**
     * 部門Repository
     */
    @Resource
    private DepartmentRepository departmentRepository;

    /**
     * 根據ID找出部門
     *
     * @param id 主鍵
     * @return 部門
     * @see Department
     */
    @Operation(summary = "根據ID找出部門")
    @GetMapping("{id}")
    public Department findByDepartmentId(
            @Parameter(
                    name = "id",
                    description = "主鍵",
                    required = true,
                    schema = @Schema(type = "string"))
            @PathVariable("id") String id) {
        com.sungyeh.domain.Department department = departmentRepository.getReferenceById(id);
        return new Department(department);
    }

    /**
     * 找出全部部門
     *
     * @return 部門
     * @see Department
     */
    @Operation(summary = "找出全部部門")
    @GetMapping
    public List<Department> findAllDepartment() {
        List<com.sungyeh.domain.Department> departments = departmentRepository.findAll();
        return departments.stream().map(Department::new).toList();
    }

    /**
     * 新增部門
     *
     * @param department 部門
     * @return 部門
     * @see Department
     */
    @Operation(summary = "新增部門")
    @PostMapping
    public Department saveDepartment(@RequestBody Department department) {
        department.setId(null);
        return new Department(departmentRepository.save(department.toEntity()));
    }

    /**
     * 更新部門
     *
     * @param department 部門
     * @param id         主鍵
     * @return 部門
     * @see Department
     */
    @Operation(summary = "更新部門")
    @PutMapping("{id}")
    public Department updateDepartment(@RequestBody Department department,
                                       @Parameter(
                                               name = "id",
                                               description = "主鍵",
                                               required = true,
                                               schema = @Schema(type = "string"))
                                       @PathVariable("id") String id) {
        com.sungyeh.domain.Department target = departmentRepository.getReferenceById(id);
        target.setName(department.getName());
        target.setNo(department.getNo());
        return new Department(departmentRepository.save(target));
    }

    /**
     * 刪除部門
     *
     * @param id 主鍵
     */
    @Operation(summary = "刪除部門")
    @DeleteMapping("{id}")
    public void deleteDepartment(
            @Parameter(
                    name = "id",
                    description = "主鍵",
                    required = true,
                    schema = @Schema(type = "string"))
            @PathVariable("id") String id) {
        departmentRepository.deleteById(id);
    }


}
