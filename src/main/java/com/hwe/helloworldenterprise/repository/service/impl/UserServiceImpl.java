package com.hwe.helloworldenterprise.repository.service.impl;
import com.hwe.helloworldenterprise.entity.User;
import com.hwe.helloworldenterprise.repository.IUserDAO;
import com.hwe.helloworldenterprise.repository.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDAO userDAO;

    @Override
    @Transactional
    public boolean delete(Long id) {
        try {
            userDAO.deleteById(id);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    @Transactional
    public User save(User user) {return userDAO.save(user);}

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userDAO.findById(id).get();
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findUserByUserName(username);
    }
}
