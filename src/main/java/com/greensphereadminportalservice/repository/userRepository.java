package com.greensphereadminportalservice.repository;


import com.greensphereadminportalservice.model.usersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface userRepository extends JpaRepository<usersModel, String> {
    usersModel findByUsername(String username);
}
