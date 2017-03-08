package me.treaba.auth.controller.resources;

import me.treaba.auth.domain.User;
import org.springframework.hateoas.ResourceSupport;

import java.util.Set;

/**
 * Created by Stanislav on 08.03.17.
 */
public class UserResource extends ResourceSupport {

  private User user;

  public UserResource(User user) {
    this.user = user;
  }

  public String getEmail() {
    return user.getEmail();
  }

  public String getDisplayName() {
    return user.getDisplayName();
  }

  public Set<String> getAuthorities() {
    return user.getAuthorities();
  }

}
