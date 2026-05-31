package io.github.DekkerDing.ecommerce.repository.jpa.mapper;

import io.github.DekkerDing.ecommerce.domain.review.ProductReview;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.ProductReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductReviewMapper {
    ProductReview toDomain(ProductReviewEntity entity);

    @Mapping(target = "id", ignore = true)
    ProductReviewEntity toEntity(ProductReview domain);

    List<ProductReview> toDomainList(List<ProductReviewEntity> entities);

    @Mapping(target = "id", ignore = true)
    List<ProductReviewEntity> toEntityList(List<ProductReview> domains);
}
