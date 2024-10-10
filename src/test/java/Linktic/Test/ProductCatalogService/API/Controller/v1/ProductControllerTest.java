package Linktic.Test.ProductCatalogService.API.Controller.v1;

import Linktic.Test.ProductCatalogService.Application.DTO.ProductDTO;
import Linktic.Test.ProductCatalogService.Application.Services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllProducts() throws Exception {
        // Configuración del mock
        ProductDTO product1 = new ProductDTO(1L, "Producto 1", "Descripción 1", 10.0, 1L);
        ProductDTO product2 = new ProductDTO(2L, "Producto 2", "Descripción 2", 20.0, 1L);
        List<ProductDTO> productList = Arrays.asList(product1, product2);

        Mockito.when(productService.getAllProducts()).thenReturn(productList);

        // Ejecución de la prueba
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Producto 1")))
                .andExpect(jsonPath("$[1].name", is("Producto 2")));
    }

    @Test
    public void testGetProductById() throws Exception {
        // Configuración del mock
        ProductDTO product = new ProductDTO(1L, "Producto 1", "Descripción 1", 10.0, 1L);
        Mockito.when(productService.getProductById(1L)).thenReturn(product);

        // Ejecución de la prueba
        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Producto 1")))
                .andExpect(jsonPath("$.price", is(10.0)));
    }

    @Test
    public void testCreateProduct() throws Exception {

        // Configuración del mock
        ProductDTO productDTO = new ProductDTO(null, "Nuevo Producto", "Nueva Descripción", 15.0, 1L);
        ProductDTO savedProduct = new ProductDTO(3L, "Nuevo Producto", "Nueva Descripción", 15.0, 1L);

        Mockito.when(productService.saveProduct(Mockito.any(ProductDTO.class))).thenReturn(savedProduct);

        // Ejecucion de la prueba
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("Nuevo Producto")))
                .andExpect(jsonPath("$.price", is(15.0)));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        // No es necesario configurar el mock para el metodo delete -- Esto se hace porque en el contexto de una prueba no queremos realmente eliminar nada, solo verificar que el controlador llame al método.
        Mockito.doNothing().when(productService).deleteProduct(1L);

        // Ejecución de la prueba
        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isOk());
    }
}