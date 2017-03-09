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
import static org.apache.commons.lang3.StringUtils.isNotBlank;

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
    encodePassword(user);
    if (isNew(user)) {
      setDefaultValues(user);
    } else {
      syncOldFieldsOnEmpty(user);
    }
    return userRepository.save(user);
  }

  private void encodePassword(User user) {
    if (isNotBlank(user.getPassword()))
      user.setPassword(passwordEncoder.encode(user.getPassword()));
  }

  private void setDefaultValues(User user) {
    if (isBlank(user.getDisplayName())) {
      user.setDisplayName(user.getEmail());
    }
    if (user.getAuthorities() == null || user.getAuthorities().isEmpty())
      user.setAuthorities(new HashSet<String>() {{
        add(ROLE_USER);
      }});
  }

  private boolean isNew(User user) {
    return !userRepository.exists(user.getEmail());
  }

  private void syncOldFieldsOnEmpty(User user) {
    User currentUser = userRepository.findOne(user.getEmail());
    if (isBlank(user.getDisplayName()))
      user.setDisplayName(currentUser.getDisplayName());
    if (isBlank(user.getPassword()))
      user.setPassword(currentUser.getPassword());
    if (user.getAuthorities() == null || user.getAuthorities().isEmpty())
      user.setAuthorities(currentUser.getAuthorities());
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
