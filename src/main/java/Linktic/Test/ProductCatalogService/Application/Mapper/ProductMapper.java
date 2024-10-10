package Linktic.Test.ProductCatalogService.Application.Mapper;

import Linktic.Test.ProductCatalogService.Application.DTO.ProductDTO;

import Linktic.Test.ProductCatalogService.Core.Entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toProductDTO(Product product);

    Product toProduct(ProductDTO productDTO);
}
