package me.treaba.auth.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Created by Stanislav on 07.03.17.
 */
@Data
@Entity
@Table(name = "authorities")
@IdClass(UserAuthorityId.class)
public class UserAuthority {

  @Id
  private String email;
  @Id
  private String authority;
}
