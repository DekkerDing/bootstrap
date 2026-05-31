package io.github.DekkerDing.customer.repository.jpa.mapper;

import io.github.DekkerDing.customer.domain.dialogue.DialogueLog;
import io.github.DekkerDing.customer.repository.jpa.entity.DialogueLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DialogueLogMapper {
    @Mapping(source = "sessionId", target = "conversationId")
    @Mapping(source = "intentDetected", target = "intent")
    @Mapping(source = "confidenceScore", target = "confidence")
    @Mapping(target = "entities", ignore = true)
    DialogueLog toDomain(DialogueLogEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "conversationId", target = "sessionId")
    @Mapping(source = "intent", target = "intentDetected")
    @Mapping(source = "confidence", target = "confidenceScore")
    @Mapping(target = "responseSource", ignore = true)
    @Mapping(target = "escalated", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    DialogueLogEntity toEntity(DialogueLog domain);

    List<DialogueLog> toDomainList(List<DialogueLogEntity> entities);

    @Mapping(target = "id", ignore = true)
    List<DialogueLogEntity> toEntityList(List<DialogueLog> domains);
}
