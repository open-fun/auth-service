package me.treaba.auth.service;

import lombok.extern.slf4j.Slf4j;
import me.treaba.auth.domain.User;
import me.treaba.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.HashSet;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by Stanislav on 06.03.17.
 */
@Slf4j
@Service
@Transactional
public class UserService {
  private static final String DEFAULT_ADMIN = "admin@treaba.me";
  public static final String ROLE_USER = "ROLE_USER";

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostConstruct
  public void initService() throws SQLException {
    if (userRepository.exists(DEFAULT_ADMIN))
      return;
    User user = new User();
    user.setEmail(DEFAULT_ADMIN);
    user.setPassword(passwordEncoder.encode("admin"));
    user.setEnabled(true);
    user.setAuthorities(new HashSet<String>() {{
      add("ROLE_ADMIN");
      add(ROLE_USER);
    }});
    userRepository.save(user);
  }

  public User save(User user) {
    if (!userRepository.exists(user.getEmail())) {
      user.setAuthorities(new HashSet<String>() {{
        add(ROLE_USER);
      }});
    }
    if (isBlank(user.getDisplayName())) {
      user.setDisplayName(user.getEmail());
    }
    return userRepository.save(user);
  }

  public Page<User> findAll(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  public void deleteByEmail(String email) {
    String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
    if (currentUser.equals(email))
      throw new IllegalStateException("Cannot delete yourself");
    userRepository.delete(email);
  }

  public User findOne(String email) {
    return userRepository.findOne(email);
  }
}
