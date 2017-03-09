package me.treaba.auth.controller.resources;

import me.treaba.auth.controller.UserController;
import me.treaba.auth.domain.User;
import me.treaba.auth.service.EncodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Stanislav on 09.03.17.
 */
@Component
public class UserResourceAssembler implements ResourceAssembler<User, UserResource> {

  @Autowired
  private EncodingService encodingService;

  @Override
  public UserResource toResource(User entity) {
    UserResource userResource = new UserResource(entity);
    userResource.add(linkTo(methodOn(UserController.class).find(encodingService.encode(entity.getEmail()))).withSelfRel());
    return userResource;
  }
}
