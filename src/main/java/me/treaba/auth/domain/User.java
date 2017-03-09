package me.treaba.auth.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
  @NotNull(message = "error.email.notnull")
  @Size(min = 10, max = 256, message = "error.email.size")
  private String email;

  @NotNull
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<String> authorities;

  private boolean enabled;
  private boolean accountExpired;
  private boolean credentialsExpired;
  private boolean accountLocked;
}
