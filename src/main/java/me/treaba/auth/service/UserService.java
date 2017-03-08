package me.treaba.auth.service;

import lombok.extern.slf4j.Slf4j;
import me.treaba.auth.domain.User;
import me.treaba.auth.domain.UserAuthority;
import me.treaba.auth.repository.UserAuthorityRepository;
import me.treaba.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

/**
 * Created by stanislav on 06.03.17.
 */
@Slf4j
@Service
@Transactional
public class UserService {
  private static final String DEFAULT_ADMIN = "admin@treaba.me";

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserAuthorityRepository userAuthorityRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostConstruct
  public void initService() throws SQLException {
    if (userRepository.existsByEmail(DEFAULT_ADMIN))
      return;
    User user = new User();
    user.setEmail(DEFAULT_ADMIN);
    user.setPassword(passwordEncoder.encode("admin"));
    user.setEnabled(true);
    userRepository.save(user);

    UserAuthority userAuthority = new UserAuthority();
    userAuthority.setEmail(DEFAULT_ADMIN);
    userAuthority.setAuthority("ROLE_ADMIN");
    userAuthorityRepository.save(userAuthority);

    userAuthority = new UserAuthority();
    userAuthority.setEmail(DEFAULT_ADMIN);
    userAuthority.setAuthority("ROLE_USER");
    userAuthorityRepository.save(userAuthority);
  }

}
