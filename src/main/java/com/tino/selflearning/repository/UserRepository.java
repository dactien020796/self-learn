package com.tino.selflearning.repository;

import com.tino.selflearning.entity.User;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

  Optional<User> findByUsername(String name);

}
