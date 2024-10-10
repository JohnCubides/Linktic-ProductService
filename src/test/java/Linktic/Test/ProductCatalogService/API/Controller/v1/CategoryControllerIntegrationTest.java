package Linktic.Test.ProductCatalogService.API.Controller.v1;

import Linktic.Test.ProductCatalogService.Application.DTO.CategoryDTO;
import Linktic.Test.ProductCatalogService.Application.Services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllCategories() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(Arrays.asList(
                new CategoryDTO(1L, "Electronics", "Electronics"),
                new CategoryDTO(2L, "Books", "Books")
        ));

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldCreateCategory() throws Exception {
        CategoryDTO newCategory = new CategoryDTO(null, "Clothing", "Clothing");
        CategoryDTO savedCategory = new CategoryDTO(1L, "Clothing", "Clothing");

        when(categoryService.saveCategory(any(CategoryDTO.class))).thenReturn(savedCategory);

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Clothing"));
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        Mockito.doNothing().when(categoryService).deleteCategory(1L);

        mockMvc.perform(delete("/api/v1/categories/1"))
                .andExpect(status().isOk());
    }
}