package Linktic.Test.ProductCatalogService.Core.Repository;

import Linktic.Test.ProductCatalogService.Core.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
