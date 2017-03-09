package me.treaba.auth.repository;

import me.treaba.auth.domain.ClientApplication;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Stanislav on 08.03.17.
 */
@Transactional
@RepositoryRestResource
public interface ClientApplicationRepository extends PagingAndSortingRepository<ClientApplication, String> {
}
