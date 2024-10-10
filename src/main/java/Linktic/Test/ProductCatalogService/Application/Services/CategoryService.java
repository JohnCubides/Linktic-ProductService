package Linktic.Test.ProductCatalogService.Application.Services;

import Linktic.Test.ProductCatalogService.Application.DTO.CategoryDTO;
import Linktic.Test.ProductCatalogService.Application.Mapper.ProductMapper;
import Linktic.Test.ProductCatalogService.Core.Entity.Category;
import Linktic.Test.ProductCatalogService.Core.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(productMapper::toCategoryDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(productMapper::toCategoryDTO)
                .orElse(null);
    }

    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = productMapper.toCategory(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return productMapper.toCategoryDTO(savedCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}