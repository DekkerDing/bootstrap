package io.github.DekkerDing.customer.repository.jpa.mapper;

import io.github.DekkerDing.customer.domain.knowledge.KnowledgeBase;
import io.github.DekkerDing.customer.repository.jpa.entity.KnowledgeBaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface KnowledgeBaseMapper {
    KnowledgeBase toDomain(KnowledgeBaseEntity entity);

    @Mapping(target = "id", ignore = true)
    KnowledgeBaseEntity toEntity(KnowledgeBase domain);

    List<KnowledgeBase> toDomainList(List<KnowledgeBaseEntity> entities);

    @Mapping(target = "id", ignore = true)
    List<KnowledgeBaseEntity> toEntityList(List<KnowledgeBase> domains);
}
