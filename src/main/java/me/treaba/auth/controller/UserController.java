package me.treaba.auth.controller;

import lombok.extern.slf4j.Slf4j;
import me.treaba.auth.controller.resources.UserResource;
import me.treaba.auth.domain.User;
import me.treaba.auth.service.EncodingService;
import me.treaba.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Stanislav on 08.03.17.
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private EncodingService encodingService;

  @RequestMapping(method = RequestMethod.POST)
  public HttpEntity<UserResource> create(@RequestBody User user) {
    log.info("Received user: {}", user);
    User savedUser = userService.save(user);
    UserResource userResource = mapToResource(savedUser);
    return new ResponseEntity<>(userResource, HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.GET)
  public PagedResources<UserResource> list(@PageableDefault(page = 0,
      size = 20,
      direction = Sort.Direction.ASC,
      sort = "email") Pageable pageable) {
    log.info("Requested pageable: {}", pageable);
    Page<UserResource> pagedResources = userService.findAll(pageable).map(this::mapToResource);
    PagedResources.PageMetadata pageMetadata = new PagedResources.PageMetadata(pagedResources.getSize(), pagedResources.getNumber(), pagedResources.getTotalElements(), pagedResources.getTotalPages());
    return new PagedResources<>(
        pagedResources.getContent(),
        pageMetadata,
        linkTo(methodOn(UserController.class).list(pageable)).withSelfRel(),
        linkTo(methodOn(UserController.class).list(pageable.first())).withRel(Link.REL_FIRST),
        linkTo(methodOn(UserController.class).list(pageable.previousOrFirst())).withRel(Link.REL_PREVIOUS),
        linkTo(methodOn(UserController.class).list(pageable.next())).withRel(Link.REL_NEXT)
    );
  }

  @RequestMapping(path = "/{email}", method = RequestMethod.GET)
  public HttpEntity<UserResource> find(@PathVariable String email) {
    UserResource userResource = mapToResource(userService.findOne(encodingService.decode(email)));
    return new ResponseEntity<>(userResource, HttpStatus.OK);
  }

  @RequestMapping(path = "/{email}", method = RequestMethod.PUT)
  public HttpEntity<UserResource> find(@PathVariable String email, User user) {
    user.setEmail(encodingService.decode(email));
    UserResource userResource = mapToResource(userService.save(user));
    return new ResponseEntity<>(userResource, HttpStatus.OK);
  }

  @RequestMapping(path = "/{email}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable String email) {
    userService.deleteByEmail(encodingService.decode(email));
  }

  private UserResource mapToResource(User savedUser) {
    UserResource userResource = new UserResource(savedUser);
    String email = encodingService.encode(savedUser.getEmail());
    userResource.add(linkTo(methodOn(UserController.class).find(email)).withSelfRel());
    return userResource;
  }
}
