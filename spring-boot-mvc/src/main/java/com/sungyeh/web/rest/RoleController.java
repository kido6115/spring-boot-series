package com.sungyeh.web.rest;

import com.sungyeh.bean.swagger.Role;
import com.sungyeh.repository.RoleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RoleController
 *
 * @author sungyeh
 */
@RestController
@RequestMapping("/rest/role")
@Tag(name = "角色")
public class RoleController {

    @Resource
    private RoleRepository roleRepository;

    /**
     * 根據ID找出角色
     *
     * @param id 主鍵
     * @return 角色
     * @see Role
     */
    @Operation(summary = "根據ID找出角色")
    @GetMapping("{id}")
    public Role findByRoleId(
            @Parameter(
                    name = "id",
                    description = "主鍵",
                    required = true,
                    schema = @Schema(type = "string"))
            @PathVariable("id") String id) {
        return new Role(roleRepository.getReferenceById(id));
    }

    /**
     * 找出全部角色
     *
     * @return 角色
     * @see Role
     */
    @Operation(summary = "找出全部角色")
    @GetMapping
    public List<Role> findAllRole() {
        return roleRepository.findAll().stream().map(Role::new).toList();
    }

    /**
     * 新增角色
     *
     * @param role 角色
     * @return 角色
     * @see Role
     */
    @Operation(summary = "新增角色")
    @PostMapping
    public Role saveRole(@RequestBody Role role) {
        role.setId(null);
        return new Role(roleRepository.save(role.toEntity()));
    }

    /**
     * 更新角色
     *
     * @param role 角色
     * @param id   主鍵
     * @return 角色
     * @see Role
     */
    @Operation(summary = "更新角色")
    @PutMapping("{id}")
    public Role updateRole(@RequestBody Role role,
                           @Parameter(
                                   name = "id",
                                   description = "主鍵",
                                   required = true,
                                   schema = @Schema(type = "string"))
                           @PathVariable("id") String id) {
        com.sungyeh.domain.Role target = roleRepository.getReferenceById(id);
        target.setName(role.getName());
        target.setNo(role.getNo());
        return new Role(roleRepository.save(target));
    }

    /**
     * 刪除角色
     *
     * @param id 主鍵
     */
    @Operation(summary = "刪除角色")
    @DeleteMapping("{id}")
    public void deleteRole(
            @Parameter(
                    name = "id",
                    description = "主鍵",
                    required = true,
                    schema = @Schema(type = "string"))
            @PathVariable("id") String id) {
        roleRepository.deleteById(id);
    }


}
