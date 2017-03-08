package me.treaba.auth.service;

import me.treaba.auth.repository.ClientApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * Created by Stanislav on 08.03.17.
 */
@Service
public class ClientApplicationService implements ClientDetailsService {

  @Autowired
  private ClientApplicationRepository clientApplicationRepository;

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    return clientApplicationRepository.findOne(clientId);
  }
}
