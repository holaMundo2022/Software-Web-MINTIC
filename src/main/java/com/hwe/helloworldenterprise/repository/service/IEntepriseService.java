package com.hwe.helloworldenterprise.repository.service;


import com.hwe.helloworldenterprise.entity.Enterprise;

import java.util.List;

public interface IEntepriseService {


    public boolean delete(Long id);

    public Enterprise save(Enterprise enterprise);

    public List<Enterprise> findAll();

    public Enterprise findById(Long id);
}
