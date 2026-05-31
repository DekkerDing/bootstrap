package io.github.DekkerDing.ecommerce.repository.jpa.mapper;

import io.github.DekkerDing.ecommerce.domain.product.Product;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * 商品 Entity 与领域模型映射器
 * Product Entity and Domain Model Mapper
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductMapper {

    /**
     * 将 Entity 转换为领域模型
     * Convert Entity to domain model
     */
    Product toDomain(ProductEntity entity);

    /**
     * 将领域模型转换为 Entity
     * Convert domain model to Entity
     */
    @Mapping(target = "id", ignore = true)
    ProductEntity toEntity(Product domain);

    /**
     * 将 Entity 列表转换为领域模型列表
     * Convert Entity list to domain model list
     */
    List<Product> toDomainList(List<ProductEntity> entities);

    /**
     * 将领域模型列表转换为 Entity 列表
     * Convert domain model list to Entity list
     */
    @Mapping(target = "id", ignore = true)
    List<ProductEntity> toEntityList(List<Product> domains);
}
