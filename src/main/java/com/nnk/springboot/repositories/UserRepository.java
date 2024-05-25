package com.nnk.springboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nnk.springboot.domain.DBUser;

@Repository
public interface UserRepository extends JpaRepository<DBUser, Integer>, JpaSpecificationExecutor<DBUser> {

	Optional<DBUser> findByUsername(String username);

}
