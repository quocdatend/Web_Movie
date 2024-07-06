package com.webphim.webphim.Service;

import com.webphim.webphim.Model.Category;
import com.webphim.webphim.Model.Movies;
import com.webphim.webphim.Reponsitory.CategoryRepository;
import com.webphim.webphim.Reponsitory.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MoviesRepository moviesRepository;
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        return category;
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setCategoryName(categoryDetails.getCategoryName());
        category.setSlug(categoryDetails.getSlug());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(category);
    }
    public void addCategoryToMovie(Long movieId, Long categoryId) {
        // Fetch the movie and category from the database
        Movies movie = moviesRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));

        // Add the category to the movie
        movie.getCategories().add(category);

        // Save the updated movie
        moviesRepository.save(movie);
    }
    public void removeCategoryFromMovie(Long movieId, Long categoryId) {
        Movies movie = moviesRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        movie.getCategories().remove(category);
        moviesRepository.save(movie);
    }
}