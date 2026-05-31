package io.github.DekkerDing.ecommerce.repository.jpa.mapper;

import io.github.DekkerDing.ecommerce.domain.order.Order;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * 订单 Entity 与领域模型映射器
 * Order Entity and Domain Model Mapper
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {OrderItemMapper.class}
)
public interface OrderMapper {

    /**
     * 将 Entity 转换为领域模型
     * Convert Entity to domain model
     */
    Order toDomain(OrderEntity entity);

    /**
     * 将领域模型转换为 Entity
     * Convert domain model to Entity
     */
    @Mapping(target = "id", ignore = true)
    OrderEntity toEntity(Order domain);

    /**
     * 将 Entity 列表转换为领域模型列表
     * Convert Entity list to domain model list
     */
    List<Order> toDomainList(List<OrderEntity> entities);

    /**
     * 将领域模型列表转换为 Entity 列表
     * Convert domain model list to Entity list
     */
    @Mapping(target = "id", ignore = true)
    List<OrderEntity> toEntityList(List<Order> domains);
}
