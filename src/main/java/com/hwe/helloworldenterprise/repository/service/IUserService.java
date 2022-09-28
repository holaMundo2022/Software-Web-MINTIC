package com.hwe.helloworldenterprise.repository.service;

import com.hwe.helloworldenterprise.entity.User;

import java.util.List;

public interface IUserService {

    //crud
    public boolean delete(Long id);

    public User save(User user);

    public List<User> findAll();

    public User findById(Long id);

    public User findByUsername(String username);
}
