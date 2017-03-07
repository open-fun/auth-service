package me.treaba.auth.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by stanislav on 08.03.17.
 */
@Data
@Embeddable
public class UserAuthorityId implements Serializable {
  private String email;
  private String authority;
}