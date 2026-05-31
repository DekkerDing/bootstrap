package io.github.DekkerDing.ecommerce.repository.jpa.mapper;

import io.github.DekkerDing.ecommerce.domain.user.User;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * 用户 Entity 与领域模型映射器
 * User Entity and Domain Model Mapper
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    /**
     * 将 Entity 转换为领域模型
     * Convert Entity to domain model
     */
    User toDomain(UserEntity entity);

    /**
     * 将领域模型转换为 Entity
     * Convert domain model to Entity
     */
    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(User domain);

    /**
     * 将 Entity 列表转换为领域模型列表
     * Convert Entity list to domain model list
     */
    List<User> toDomainList(List<UserEntity> entities);

    /**
     * 将领域模型列表转换为 Entity 列表
     * Convert domain model list to Entity list
     */
    @Mapping(target = "id", ignore = true)
    List<UserEntity> toEntityList(List<User> domains);
}
