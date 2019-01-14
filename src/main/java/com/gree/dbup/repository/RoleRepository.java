package com.gree.dbup.repository;

import com.gree.dbup.dbexpand.jpa.repository.ExpandJpaRepository;
import com.gree.dbup.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends ExpandJpaRepository<Role, Long> {

}
