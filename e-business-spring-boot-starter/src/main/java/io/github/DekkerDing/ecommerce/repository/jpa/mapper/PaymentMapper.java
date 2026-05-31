package io.github.DekkerDing.ecommerce.repository.jpa.mapper;

import io.github.DekkerDing.ecommerce.domain.payment.Payment;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * 支付 Entity 与领域模型映射器
 * Payment Entity and Domain Model Mapper
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PaymentMapper {

    /**
     * 将 Entity 转换为领域模型
     * Convert Entity to domain model
     */
    Payment toDomain(PaymentEntity entity);

    /**
     * 将领域模型转换为 Entity
     * Convert domain model to Entity
     */
    @Mapping(target = "id", ignore = true)
    PaymentEntity toEntity(Payment domain);

    /**
     * 将 Entity 列表转换为领域模型列表
     * Convert Entity list to domain model list
     */
    List<Payment> toDomainList(List<PaymentEntity> entities);

    /**
     * 将领域模型列表转换为 Entity 列表
     * Convert domain model list to Entity list
     */
    @Mapping(target = "id", ignore = true)
    List<PaymentEntity> toEntityList(List<Payment> domains);
}
