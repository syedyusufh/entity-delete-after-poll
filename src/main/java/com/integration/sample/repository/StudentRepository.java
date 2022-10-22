package com.integration.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.integration.sample.repository.dao.Student;

@NoRepositoryBean
public interface StudentRepository extends JpaRepository<Student, Long> {

}
