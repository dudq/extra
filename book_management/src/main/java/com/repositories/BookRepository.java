package com.repositories;

import com.models.Book;
import com.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    Page<Book> findAllByCategory(Category category, Pageable pageable);

    Page<Book> findAllByOrderByDateOfPurchaseAscPriceDesc(Pageable pageable);

    Page<Book> findAllByNameContaining(String s, Pageable pageable);
}
