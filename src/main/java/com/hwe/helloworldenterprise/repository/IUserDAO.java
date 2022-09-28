package com.hwe.helloworldenterprise.repository;

import com.hwe.helloworldenterprise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserDAO extends JpaRepository<User, Long> {

    @Query("from User u where u.userName = :username")
    public User findUserByUserName(String username);

}
