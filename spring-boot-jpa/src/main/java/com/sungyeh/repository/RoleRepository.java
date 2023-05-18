package com.sungyeh.repository;

import com.sungyeh.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RoleRepository
 *
 * @author sungyeh
 */
public interface RoleRepository extends JpaRepository<Role, String> {
}
