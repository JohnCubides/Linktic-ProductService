package Linktic.Test.ProductCatalogService.Application.Services;

import Linktic.Test.ProductCatalogService.Application.DTO.ProductDTO;
import Linktic.Test.ProductCatalogService.Application.Mapper.ProductMapper;
import Linktic.Test.ProductCatalogService.Core.Entity.Product;
import Linktic.Test.ProductCatalogService.Core.Exception.ProductNotFoundException;
import Linktic.Test.ProductCatalogService.Core.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public List<ProductDTO> getAllProducts() {

        return productRepository.findAll().stream()
                .map(productMapper::toProductDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toProductDTO)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = productMapper.toProduct(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toProductDTO(savedProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
