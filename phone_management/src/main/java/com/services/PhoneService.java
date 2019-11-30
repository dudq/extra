package com.services;

import com.models.Category;
import com.models.Phone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PhoneService {

    Page<Phone> findAll(Pageable pageable);
    Phone findById(Long id);
    void save(Phone phone);
    void remove(Long id);
    Page<Phone> findAllByCategory(Category category, Pageable pageable);
    Page<Phone> findAllByOrderByDateOfPurchaseAscPriceDesc(Pageable pageable);
    Page<Phone> findAllByNameContaining(String s, Pageable pageable);

}
