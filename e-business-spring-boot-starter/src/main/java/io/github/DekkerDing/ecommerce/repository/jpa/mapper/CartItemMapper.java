package io.github.DekkerDing.ecommerce.repository.jpa.mapper;

import io.github.DekkerDing.ecommerce.domain.cart.CartItem;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CartItemMapper {
    @Mapping(target = "productName", ignore = true)
    @Mapping(target = "productImage", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "onSale", ignore = true)
    @Mapping(target = "hasStock", ignore = true)
    @Mapping(target = "stock", ignore = true)
    CartItem toDomain(CartItemEntity entity);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "cartId", ignore = true)
    CartItemEntity toEntity(CartItem domain);
    List<CartItem> toDomainList(List<CartItemEntity> entities);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "cartId", ignore = true)
    List<CartItemEntity> toEntityList(List<CartItem> domains);
}
