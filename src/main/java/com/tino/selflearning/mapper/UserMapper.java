package com.tino.selflearning.mapper;

import com.tino.selflearning.dto.UserDto;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ACCESSOR_ONLY,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    componentModel = "spring"
)
public interface UserMapper {

  UserDto mapToDto(com.tino.selflearning.entity.User entity);

  com.tino.selflearning.entity.User mapToEntity(UserDto dto);
}
