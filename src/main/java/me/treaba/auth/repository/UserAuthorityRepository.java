package me.treaba.auth.repository;

import me.treaba.auth.domain.UserAuthority;
import me.treaba.auth.domain.UserAuthorityId;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Stanislav on 07.03.17.
 */
public interface UserAuthorityRepository extends CrudRepository<UserAuthority, UserAuthorityId> {
}
