package com.tino.selflearning.mapper;

import com.tino.selflearning.dto.ProductDto;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ACCESSOR_ONLY,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    componentModel = "spring"
)
public interface ProductMapper {

  @Mapping(source = "createdDate", target = "createdDate", dateFormat = "dd/MM/yyyy")
  ProductDto mapToDto(com.tino.selflearning.entity.Product entity);

  com.tino.selflearning.entity.Product mapToEntity(ProductDto dto);
}
