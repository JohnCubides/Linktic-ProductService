package Linktic.Test.ProductCatalogService.Application.Services.Unit;

import Linktic.Test.ProductCatalogService.Application.DTO.CategoryDTO;
import Linktic.Test.ProductCatalogService.Application.Mapper.ProductMapper;
import Linktic.Test.ProductCatalogService.Application.Services.CategoryService;
import Linktic.Test.ProductCatalogService.Core.Entity.Category;
import Linktic.Test.ProductCatalogService.Core.Repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllCategories() {
        // Arrange
        Category category1 = new Category(1L, "Category 1", "Description 1");
        Category category2 = new Category(2L, "Category 2", "Description 2");
        CategoryDTO categoryDTO1 = new CategoryDTO(1L, "Category 1", "Description 1");
        CategoryDTO categoryDTO2 = new CategoryDTO(2L, "Category 2", "Description 2");

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));
        when(productMapper.toCategoryDTO(category1)).thenReturn(categoryDTO1);
        when(productMapper.toCategoryDTO(category2)).thenReturn(categoryDTO2);

        // Act
        List<CategoryDTO> categories = categoryService.getAllCategories();

        // Assert
        assertEquals(2, categories.size());
        verify(categoryRepository, times(1)).findAll();
        verify(productMapper, times(2)).toCategoryDTO(any(Category.class));
    }

    @Test
    void shouldReturnCategoryById() {
        // Arrange
        Category category = new Category(1L, "Category 1", "Description 1");
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Category 1", "Description 1");

        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(category));
        when(productMapper.toCategoryDTO(category)).thenReturn(categoryDTO);

        // Act
        CategoryDTO result = categoryService.getCategoryById(1L);

        // Assert
        assertEquals(categoryDTO, result);
        verify(categoryRepository, times(1)).findById(1L);
        verify(productMapper, times(1)).toCategoryDTO(category);
    }

    @Test
    void shouldReturnNullWhenCategoryNotFound() {
        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act
        CategoryDTO result = categoryService.getCategoryById(1L);

        // Assert
        assertNull(result);
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void shouldSaveCategory() {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO(null, "Category 1", "Description 1");
        Category category = new Category(null, "Category 1", "Description 1");
        Category savedCategory = new Category(1L, "Category 1", "Description 1");
        CategoryDTO savedCategoryDTO = new CategoryDTO(1L, "Category 1", "Description 1");

        when(productMapper.toCategory(categoryDTO)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        when(productMapper.toCategoryDTO(savedCategory)).thenReturn(savedCategoryDTO);

        // Act
        CategoryDTO result = categoryService.saveCategory(categoryDTO);

        // Assert
        assertEquals(savedCategoryDTO, result);
        verify(categoryRepository, times(1)).save(category);
        verify(productMapper, times(1)).toCategory(categoryDTO);
        verify(productMapper, times(1)).toCategoryDTO(savedCategory);
    }

    @Test
    void shouldDeleteCategory() {
        // Arrange
        doNothing().when(categoryRepository).deleteById(1L);

        // Act
        categoryService.deleteCategory(1L);

        // Assert
        verify(categoryRepository, times(1)).deleteById(1L);
    }
}