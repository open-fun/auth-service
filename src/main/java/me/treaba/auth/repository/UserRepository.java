package me.treaba.auth.repository;

import me.treaba.auth.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Stanislav on 06.03.17.
 */
public interface UserRepository extends PagingAndSortingRepository<User, String> {
}
