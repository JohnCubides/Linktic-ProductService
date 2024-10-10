package Linktic.Test.ProductCatalogService.Configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Product Catalog API",
                version = "v1",
                description = "API documentation for the Product Catalog Service"
        )
)
public class SwaggerConfig {
}

