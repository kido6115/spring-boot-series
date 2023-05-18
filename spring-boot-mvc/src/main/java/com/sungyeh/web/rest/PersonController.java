package com.sungyeh.web.rest;

import com.sungyeh.bean.swagger.Department;
import com.sungyeh.bean.swagger.Role;
import com.sungyeh.domain.Person;
import com.sungyeh.repository.PersonRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * PersonController
 *
 * @author sungyeh
 */
@RestController
@RequestMapping("/rest/user")
@Tag(name = "使用者功能")
public class PersonController {

    @Resource
    private PersonRepository personRepository;

    /**
     * 根據ID找出使用者
     *
     * @param id 主鍵
     * @return 使用者
     * @see com.sungyeh.bean.swagger.Person
     */
    @Operation(summary = "根據ID找出使用者")
    @GetMapping("/{id}")
    public com.sungyeh.bean.swagger.Person findByUserId(@Parameter(
            name = "id",
            description = "主鍵",
            required = true,
            schema = @Schema(type = "string")) @PathVariable("id") String id) {
        Person person = personRepository.getReferenceById(id);
        com.sungyeh.bean.swagger.Person personDto = new com.sungyeh.bean.swagger.Person();
        personDto.setId(person.getId());
        personDto.setUsername(person.getUsername());
        personDto.setDepartment(new Department(person.getDepartment()));
        List<Role> roles = person.getRoles().stream().map(Role::new).toList();
        personDto.setRoles(roles);
        return personDto;
    }

    /**
     * 找出全部使用者
     *
     * @return 使用者
     * @see com.sungyeh.bean.swagger.Person
     */
    @Operation(summary = "找出全部使用者")
    @GetMapping
    public List<com.sungyeh.bean.swagger.Person> findAllUser() {
        List<Person> people = personRepository.findAll();
        List<com.sungyeh.bean.swagger.Person> dto = new ArrayList<>();
        for (Person person : people) {
            com.sungyeh.bean.swagger.Person personDto = new com.sungyeh.bean.swagger.Person();
            personDto.setId(person.getId());
            personDto.setUsername(person.getUsername());
            personDto.setDepartment(new Department(person.getDepartment()));
            List<Role> roles = person.getRoles().stream().map(Role::new).toList();
            personDto.setRoles(roles);
            dto.add(personDto);
        }
        return dto;
    }

}
