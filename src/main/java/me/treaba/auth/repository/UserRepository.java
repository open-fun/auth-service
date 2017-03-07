package me.treaba.auth.repository;

import me.treaba.auth.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by stanislav on 06.03.17.
 */
public interface UserRepository extends CrudRepository<User, String> {
  boolean existsByEmail(String email);

  User findByEmail(String email);
}
