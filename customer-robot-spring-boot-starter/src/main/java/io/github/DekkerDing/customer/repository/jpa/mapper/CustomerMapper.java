package io.github.DekkerDing.customer.repository.jpa.mapper;

import io.github.DekkerDing.customer.domain.customer.Customer;
import io.github.DekkerDing.customer.repository.jpa.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    Customer toDomain(CustomerEntity entity);

    @Mapping(target = "id", ignore = true)
    CustomerEntity toEntity(Customer domain);

    List<Customer> toDomainList(List<CustomerEntity> entities);

    @Mapping(target = "id", ignore = true)
    List<CustomerEntity> toEntityList(List<Customer> domains);
}
