package com.controllers;

import com.models.Book;
import com.models.Category;
import com.services.BookService;
import com.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/books")
    public ModelAndView showPhoneList(@RequestParam("s") Optional<String> s, @PageableDefault(size = 5) Pageable pageable) {
        Page<Book> phones;
        if (s.isPresent()) {
            phones = bookService.findAllByNameContaining(s.get(), pageable);
        } else {
            phones = bookService.findAll(pageable);
        }

        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", phones);
        return modelAndView;
    }

    @GetMapping("/book/create")
    public ModelAndView showCreatePhoneForm() {
        ModelAndView modelAndView = new ModelAndView("/book/create");
        modelAndView.addObject("book", new Book());
        return modelAndView;
    }

    @PostMapping("/book/create")
    public ModelAndView saveNewProduct(@ModelAttribute("book") Book book) {

        bookService.save(book);

        ModelAndView modelAndView = new ModelAndView("/book/create");
        modelAndView.addObject("book", new Book());
        modelAndView.addObject("message", "New book created successfully");
        return modelAndView;
    }

    @GetMapping("/book/edit/{id}")
    public ModelAndView showEditPhoneForm(@PathVariable("id") Long id) {
        Book book = bookService.findById(id);

        if (book != null) {
            ModelAndView modelAndView = new ModelAndView("/book/edit");
            modelAndView.addObject("book", book);
            return modelAndView;
        } else {
            return new ModelAndView("error");
        }
    }

    @PostMapping("book/edit")
    public ModelAndView saveEditedPhone(@ModelAttribute("book") Book book) {
        bookService.save(book);

        ModelAndView modelAndView = new ModelAndView("/book/edit");
        modelAndView.addObject("book", book);
        modelAndView.addObject("message", "book updated successfully");
        return modelAndView;
    }

    @GetMapping("/book/delete/{id}")
    public ModelAndView showDeletePhoneForm(@PathVariable("id") Long id) {
        Book book = bookService.findById(id);

        if (book != null) {
            ModelAndView modelAndView = new ModelAndView("/book/delete");
            modelAndView.addObject("book", book);
            return modelAndView;
        } else {
            return new ModelAndView("error");
        }
    }

    @PostMapping("/book/delete")
    public ModelAndView deletedPhone(@ModelAttribute("book") Book book, Pageable pageable) {
        bookService.remove(book.getId());

        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", bookService.findAll(pageable));
        return modelAndView;
    }

    @GetMapping("/book/view/{id}")
    public ModelAndView viewPhoneDetail(@PathVariable("id") Long id) {
        Book book = bookService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/book/view");
        modelAndView.addObject("book", book);
        return modelAndView;
    }

    @GetMapping("/searchByCategory")
    public ModelAndView getPhoneByCategory(@RequestParam("search") Long categoryId, Pageable pageable) {
        Page<Book> phones;
        if (categoryId == -1) {
            phones = bookService.findAll(pageable);
        } else {
            Category category = categoryService.findById(categoryId);
            phones = bookService.findAllByCategory(category, pageable);
        }

        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", phones);
        modelAndView.addObject("search", categoryId);
        return modelAndView;
    }

    @GetMapping("/sort")
    public ModelAndView sortByDateAndPrice(Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("book/list");
        modelAndView.addObject("books", bookService.findAllByOrderByDateOfPurchaseAscPriceDesc(pageable));
        return modelAndView;
    }
}
