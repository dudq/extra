package com.services.impl;

import com.models.Category;
import com.models.Phone;
import com.repositories.PhoneRepository;
import com.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Override
    public Page<Phone> findAll(Pageable pageable) {
        return phoneRepository.findAll(pageable);
    }

    @Override
    public Phone findById(Long id) {
        return phoneRepository.findOne(id);
    }

    @Override
    public void save(Phone phone) {
        phoneRepository.save(phone);
    }

    @Override
    public void remove(Long id) {
        phoneRepository.delete(id);
    }

    @Override
    public Page<Phone> findAllByCategory(Category category, Pageable pageable) {
        return phoneRepository.findAllByCategory(category,pageable);
    }

    @Override
    public Page<Phone> findAllByOrderByDateOfPurchaseAscPriceDesc(Pageable pageable) {
        return phoneRepository.findAllByOrderByDateOfPurchaseAscPriceDesc(pageable);
    }

    @Override
    public Page<Phone> findAllByNameContaining(String s, Pageable pageable) {
        return phoneRepository.findAllByNameContaining(s, pageable);
    }
}
