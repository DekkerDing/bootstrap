package io.github.DekkerDing.customer.repository.jpa.mapper;

import io.github.DekkerDing.customer.domain.session.Session;
import io.github.DekkerDing.customer.repository.jpa.entity.SessionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SessionMapper {

    Session toDomain(SessionEntity entity);

    @Mapping(target = "id", ignore = true)
    SessionEntity toEntity(Session domain);

    List<Session> toDomainList(List<SessionEntity> entities);

    @Mapping(target = "id", ignore = true)
    List<SessionEntity> toEntityList(List<Session> domains);
}
