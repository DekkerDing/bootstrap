package io.github.DekkerDing.ecommerce.repository.jpa.mapper;

import io.github.DekkerDing.ecommerce.domain.member.Member;
import io.github.DekkerDing.ecommerce.repository.jpa.entity.MemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MemberMapper {
    Member toDomain(MemberEntity entity);

    @Mapping(target = "id", ignore = true)
    MemberEntity toEntity(Member domain);

    List<Member> toDomainList(List<MemberEntity> entities);

    @Mapping(target = "id", ignore = true)
    List<MemberEntity> toEntityList(List<Member> domains);
}
