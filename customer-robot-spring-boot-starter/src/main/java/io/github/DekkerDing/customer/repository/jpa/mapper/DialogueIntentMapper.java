package io.github.DekkerDing.customer.repository.jpa.mapper;

import io.github.DekkerDing.customer.domain.dialogue.DialogueIntent;
import io.github.DekkerDing.customer.repository.jpa.entity.DialogueIntentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DialogueIntentMapper {
    DialogueIntent toDomain(DialogueIntentEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DialogueIntentEntity toEntity(DialogueIntent domain);

    List<DialogueIntent> toDomainList(List<DialogueIntentEntity> entities);

    @Mapping(target = "id", ignore = true)
    List<DialogueIntentEntity> toEntityList(List<DialogueIntent> domains);
}
