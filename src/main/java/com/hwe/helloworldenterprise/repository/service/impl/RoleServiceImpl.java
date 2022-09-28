package com.hwe.helloworldenterprise.repository.service.impl;

import com.hwe.helloworldenterprise.entity.Role;
import com.hwe.helloworldenterprise.repository.IRoleDao;
import com.hwe.helloworldenterprise.repository.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleDao.findAll();
    }
}
