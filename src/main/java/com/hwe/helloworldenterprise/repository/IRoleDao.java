package com.hwe.helloworldenterprise.repository;

import com.hwe.helloworldenterprise.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleDao extends JpaRepository<Role,Integer> {
}
