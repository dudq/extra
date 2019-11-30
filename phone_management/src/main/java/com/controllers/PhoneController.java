package com.controllers;

import com.models.Category;
import com.models.Phone;
import com.services.CategoryService;
import com.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/phones")
    public ModelAndView showPhoneList(@RequestParam("s") Optional<String> s, @PageableDefault(size = 10) Pageable pageable) {
        Page<Phone> phones;
        if (s.isPresent()) {
            phones = phoneService.findAllByNameContaining(s.get(), pageable);
        } else  {
            phones = phoneService.findAll(pageable);
        }

        ModelAndView modelAndView = new ModelAndView("/phone/list");
        modelAndView.addObject("phones", phones);
        return modelAndView;
    }

    @GetMapping("/phone/create")
    public ModelAndView showCreatePhoneForm() {
        ModelAndView modelAndView = new ModelAndView("/phone/create");
        modelAndView.addObject("phone",new Phone());
        return modelAndView;
    }

    @PostMapping("/phone/create")
    public ModelAndView saveNewProduct(@ModelAttribute("phone") Phone phone) {

        phoneService.save(phone);

        ModelAndView modelAndView = new ModelAndView("/phone/create");
        modelAndView.addObject("phone",new Phone());
        modelAndView.addObject("message","New phone created successfully");
        return modelAndView;
    }

    @GetMapping("/phone/edit/{id}")
    public ModelAndView showEditPhoneForm(@PathVariable("id") Long id) {
        Phone phone = phoneService.findById(id);

        if (phone != null) {
            ModelAndView modelAndView = new ModelAndView("/phone/edit");
            modelAndView.addObject("phone", phone);
            return modelAndView;
        } else {
            return new ModelAndView("error");
        }
    }

    @PostMapping("phone/edit")
    public ModelAndView saveEditedPhone(@ModelAttribute("phone") Phone phone) {
        phoneService.save(phone);

        ModelAndView modelAndView = new ModelAndView("/phone/edit");
        modelAndView.addObject("phone", phone);
        modelAndView.addObject("message","Phone updated successfully");
        return modelAndView;
    }

    @GetMapping("/phone/delete/{id}")
    public ModelAndView showDeletePhoneForm(@PathVariable("id") Long id) {
        Phone phone = phoneService.findById(id);

        if (phone != null) {
            ModelAndView modelAndView = new ModelAndView("/phone/delete");
            modelAndView.addObject("phone",phone);
            return modelAndView;
        } else {
            return new ModelAndView("error");
        }
    }

    @PostMapping("/phone/delete")
    public ModelAndView deletedPhone(@ModelAttribute("phone") Phone phone,Pageable pageable) {
        phoneService.remove(phone.getId());

        ModelAndView modelAndView = new ModelAndView("/phone/list");
        modelAndView.addObject("phones", phoneService.findAll(pageable));
        return modelAndView;
    }

    @GetMapping("/phone/view/{id}")
    public ModelAndView viewPhoneDetail(@PathVariable("id") Long id) {
        Phone phone = phoneService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/phone/view");
        modelAndView.addObject("phone", phone);
        return modelAndView;
    }

    @GetMapping("/searchByCategory")
    public ModelAndView getPhoneByCategory(@RequestParam("search") Long categoryId, Pageable pageable) {
        Page<Phone> phones;
        if (categoryId == -1) {
            phones = phoneService.findAll(pageable);
        } else {
            Category category = categoryService.findById(categoryId);
            phones = phoneService.findAllByCategory(category,pageable);
        }

        ModelAndView modelAndView = new ModelAndView("/phone/list");
        modelAndView.addObject("phones",phones);
        modelAndView.addObject("search",categoryId);
        return modelAndView;
    }
    @GetMapping("/sort")
    public ModelAndView sortByDateAndDate(Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("phone/list");
        modelAndView.addObject("phones", phoneService.findAllByOrderByDateOfPurchaseAscPriceDesc(pageable));
        return modelAndView;
    }
}
