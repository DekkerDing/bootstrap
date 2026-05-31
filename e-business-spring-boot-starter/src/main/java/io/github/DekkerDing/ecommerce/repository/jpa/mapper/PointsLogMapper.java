package io.github.DekkerDing.ecommerce.repository.jpa.mapper;

import io.github.DekkerDing.ecommerce.domain.member.PointsLog;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.PointsLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PointsLogMapper {
    PointsLog toDomain(PointsLogEntity entity);

    @Mapping(target = "id", ignore = true)
    PointsLogEntity toEntity(PointsLog domain);

    List<PointsLog> toDomainList(List<PointsLogEntity> entities);

    @Mapping(target = "id", ignore = true)
    List<PointsLogEntity> toEntityList(List<PointsLog> domains);
}
