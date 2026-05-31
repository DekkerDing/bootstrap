package io.github.DekkerDing.customer.repository.jpa.mapper;

import io.github.DekkerDing.customer.domain.message.Message;
import io.github.DekkerDing.customer.repository.jpa.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MessageMapper {

    Message toDomain(MessageEntity entity);

    @Mapping(target = "id", ignore = true)
    MessageEntity toEntity(Message domain);

    List<Message> toDomainList(List<MessageEntity> entities);

    @Mapping(target = "id", ignore = true)
    List<MessageEntity> toEntityList(List<Message> domains);
}
