package com.webphim.webphim.Controller.Admin;

import com.webphim.webphim.Model.Category;
import com.webphim.webphim.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("")
    public String getAllCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "Admin/categories";
    }

    // Form thêm mới danh mục
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("title", "Thêm mới danh mục");
        model.addAttribute("action", "/categories");
        return "Admin/category-form";
    }

    // Xử lý thêm mới danh mục
    @PostMapping
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryService.createCategory(category);
        return "redirect:/categories";
    }

    // Form sửa danh mục
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        model.addAttribute("title", "Chỉnh sửa danh mục");
        model.addAttribute("action", "/categories/" + id);
        return "Admin/category-form";
    }
    // Xử lý cập nhật danh mục
    @PutMapping("/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute("category") Category categoryDetails) {
        categoryService.updateCategory(id, categoryDetails);
        return "redirect:/categories";
    }

    // Xóa danh mục
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}
