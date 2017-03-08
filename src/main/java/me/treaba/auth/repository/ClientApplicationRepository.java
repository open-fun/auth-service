package me.treaba.auth.repository;

import me.treaba.auth.domain.ClientApplication;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Stanislav on 08.03.17.
 */
public interface ClientApplicationRepository extends CrudRepository<ClientApplication, String> {
}
