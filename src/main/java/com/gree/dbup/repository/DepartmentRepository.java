package com.gree.dbup.repository;

import com.gree.dbup.dbexpand.jpa.repository.ExpandJpaRepository;
import com.gree.dbup.entity.Department;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends ExpandJpaRepository<Department, Long> {
}
