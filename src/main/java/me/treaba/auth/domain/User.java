package me.treaba.auth.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by stanislav on 06.03.17.
 */
@Data
@Entity
@Table(name = "users")
public class User {

  private String display_name;

  @Id
  @NotBlank
  private String email;

  @NotBlank
  private String password;

  private boolean enabled;
}
