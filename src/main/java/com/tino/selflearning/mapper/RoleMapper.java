package com.tino.selflearning.mapper;

import com.tino.selflearning.dto.RoleDto;
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
public interface RoleMapper {

  RoleDto mapToDto(com.tino.selflearning.entity.Role entity);

  com.tino.selflearning.entity.Role mapToEntity(RoleDto dto);
}
