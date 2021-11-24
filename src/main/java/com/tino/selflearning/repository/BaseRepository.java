package com.tino.selflearning.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, Id> extends JpaRepository<T, Id> {

  default <DTO> List<DTO> findAll(Pageable page, Function<T, DTO> mapper) {
    return findAll(page).stream()
        .map(mapper)
        .collect(Collectors.toList());
  }

  default <DTO> DTO findById(Id id, Function<T, DTO> mapper) {
    Optional<T> entity = findById(id);
    if (entity.isEmpty()) {
      String message = String.format("No item with id = %s found", id);
      throw new EntityNotFoundException(message);
    }
    return mapper.apply(entity.get());
  }

  default <DTO> DTO save(T object, Function<T, DTO> mapper) {
    T saved = save(object);
    return mapper.apply(saved);
  }
}
