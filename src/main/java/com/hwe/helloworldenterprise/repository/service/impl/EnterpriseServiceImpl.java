package com.hwe.helloworldenterprise.repository.service.impl;
import com.hwe.helloworldenterprise.entity.Enterprise;
import com.hwe.helloworldenterprise.repository.IEnterpriseDAO;
import com.hwe.helloworldenterprise.repository.service.IEntepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnterpriseServiceImpl implements IEntepriseService {

    @Autowired
    private IEnterpriseDAO enterpriseDAO;

    @Override
    @Transactional
    public boolean delete(Long id) {
        try {
            enterpriseDAO.deleteById(id);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    @Override
    @Transactional
    public Enterprise save(Enterprise enterprise) {

        return enterpriseDAO.save(enterprise);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enterprise> findAll() {

        return enterpriseDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Enterprise findById(Long id) {
        return enterpriseDAO.findById(id).get();
    }
}
