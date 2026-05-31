package io.github.DekkerDing.customer.repository.jpa.mapper;

import io.github.DekkerDing.customer.domain.ticket.Ticket;
import io.github.DekkerDing.customer.repository.jpa.entity.TicketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TicketMapper {
    Ticket toDomain(TicketEntity entity);

    @Mapping(target = "id", ignore = true)
    TicketEntity toEntity(Ticket domain);

    List<Ticket> toDomainList(List<TicketEntity> entities);

    @Mapping(target = "id", ignore = true)
    List<TicketEntity> toEntityList(List<Ticket> domains);
}
