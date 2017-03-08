package me.treaba.auth.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Stanislav on 06.03.17.
 */
@Data
@Entity
@Table(name = "users")
public class User {

  private String displayName;

  @Id
  private String email;

  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<String> authorities;

  private boolean enabled;
  private boolean accountExpired;
  private boolean credentialsExpired;
  private boolean accountLocked;
}
