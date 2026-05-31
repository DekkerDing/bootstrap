package io.github.DekkerDing.customer.repository.jpa.mapper;

import io.github.DekkerDing.customer.domain.conversation.Conversation;
import io.github.DekkerDing.customer.repository.jpa.entity.ConversationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ConversationMapper {

    @Mapping(target = "messages", ignore = true)
    Conversation toDomain(ConversationEntity entity);

    @Mapping(target = "id", ignore = true)
    ConversationEntity toEntity(Conversation domain);

    List<Conversation> toDomainList(List<ConversationEntity> entities);

    @Mapping(target = "id", ignore = true)
    List<ConversationEntity> toEntityList(List<Conversation> domains);
}
