package com.repositories;

import com.models.Category;
import com.models.Phone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PhoneRepository extends PagingAndSortingRepository<Phone, Long> {
    Page<Phone> findAllByCategory(Category category, Pageable pageable);
    Page<Phone> findAllByOrderByDateOfPurchaseAscPriceDesc(Pageable pageable);
    Page<Phone> findAllByNameContaining(String s, Pageable pageable);
}
