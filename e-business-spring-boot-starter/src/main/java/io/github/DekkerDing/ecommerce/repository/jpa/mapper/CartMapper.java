package io.github.DekkerDing.ecommerce.repository.jpa.mapper;

import io.github.DekkerDing.ecommerce.domain.cart.Cart;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {CartItemMapper.class}
)
public interface CartMapper {
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "selectedItems", ignore = true)
    Cart toDomain(CartEntity entity);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    CartEntity toEntity(Cart domain);
    List<Cart> toDomainList(List<CartEntity> entities);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    List<CartEntity> toEntityList(List<Cart> domains);
}
