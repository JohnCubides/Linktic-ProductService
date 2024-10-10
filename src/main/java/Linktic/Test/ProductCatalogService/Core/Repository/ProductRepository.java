package Linktic.Test.ProductCatalogService.Core.Repository;

import Linktic.Test.ProductCatalogService.Core.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
