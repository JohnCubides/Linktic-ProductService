package Linktic.Test.ProductCatalogService.Application.Services.Unit;

import Linktic.Test.ProductCatalogService.Application.DTO.ProductDTO;
import Linktic.Test.ProductCatalogService.Application.Mapper.ProductMapper;
import Linktic.Test.ProductCatalogService.Application.Services.ProductService;
import Linktic.Test.ProductCatalogService.Core.Entity.Category;
import Linktic.Test.ProductCatalogService.Core.Entity.Product;
import Linktic.Test.ProductCatalogService.Core.Exception.ProductNotFoundException;
import Linktic.Test.ProductCatalogService.Core.Repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllProducts() {
        // Arrange
        Product product1 = new Product(1L, "Product 1", "Description 1", 100.0, new Category(1L,"test","des"));
        Product product2 = new Product(2L, "Product 2", "Description 2", 150.0, new Category(1L,"test","des"));
        ProductDTO productDTO1 = new ProductDTO(1L, "Product 1", "Description 1", 100.0, 1L);
        ProductDTO productDTO2 = new ProductDTO(2L, "Product 2", "Description 2", 150.0, 1L);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        when(productMapper.toProductDTO(product1)).thenReturn(productDTO1);
        when(productMapper.toProductDTO(product2)).thenReturn(productDTO2);

        List<ProductDTO> products = productService.getAllProducts();

        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(2)).toProductDTO(any(Product.class));
    }

    @Test
    void shouldReturnProductById() {

        Product product = new Product(1L, "Product 1", "Description 1", 100.0, new Category(1L,"test","des"));
        ProductDTO productDTO = new ProductDTO(1L, "Product 1", "Description 1", 100.0, 1L);

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        when(productMapper.toProductDTO(product)).thenReturn(productDTO);


        ProductDTO result = productService.getProductById(1L);


        assertEquals(productDTO, result);
        verify(productRepository, times(1)).findById(1L);
        verify(productMapper, times(1)).toProductDTO(product);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void shouldSaveProduct() {
        // Arrange
        ProductDTO productDTO = new ProductDTO(null, "Product 1", "Description 1", 100.0, 1L);
        Product product = new Product(null, "Product 1", "Description 1", 100.0, new Category(1L,"test","des"));
        Product savedProduct = new Product(1L, "Product 1", "Description 1", 100.0, new Category(1L,"test","des"));
        ProductDTO savedProductDTO = new ProductDTO(1L, "Product 1", "Description 1", 100.0, 1L);

        when(productMapper.toProduct(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(productMapper.toProductDTO(savedProduct)).thenReturn(savedProductDTO);

        // Act
        ProductDTO result = productService.saveProduct(productDTO);

        // Assert
        assertEquals(savedProductDTO, result);
        verify(productRepository, times(1)).save(product);
        verify(productMapper, times(1)).toProduct(productDTO);
        verify(productMapper, times(1)).toProductDTO(savedProduct);
    }

}

