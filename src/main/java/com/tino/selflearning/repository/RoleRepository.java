package com.tino.selflearning.repository;

import com.tino.selflearning.entity.Role;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {

  Optional<Role> findByName(String name);
}
