package Linktic.Test.ProductCatalogService.Application.Mapper;

import Linktic.Test.ProductCatalogService.Application.DTO.CategoryDTO;
import Linktic.Test.ProductCatalogService.Application.DTO.ProductDTO;

import Linktic.Test.ProductCatalogService.Core.Entity.Category;
import Linktic.Test.ProductCatalogService.Core.Entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ProductDTO toProductDTO(Product product);

    @Mapping(source = "categoryId", target = "category.id")
    Product toProduct(ProductDTO productDTO);

    CategoryDTO toCategoryDTO(Category category);

    Category toCategory(CategoryDTO categoryDTO);
}
