package me.treaba.auth.controller;

import lombok.extern.slf4j.Slf4j;
import me.treaba.auth.controller.resources.UserResource;
import me.treaba.auth.controller.resources.UserResourceAssembler;
import me.treaba.auth.domain.User;
import me.treaba.auth.service.EncodingService;
import me.treaba.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

  @Autowired
  private UserResourceAssembler assembler;

  @RequestMapping(method = RequestMethod.POST)
  public HttpEntity<UserResource> create(@Validated @RequestBody User user) {
    User savedUser = userService.save(user);
    return new ResponseEntity<>(assembler.toResource(savedUser), HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.GET)
  public HttpEntity<PagedResources<UserResource>> list(@PageableDefault(sort = "email") Pageable pageable,
                                                       PagedResourcesAssembler<User> pagedResourcesAssembler) {
    Page<User> usersPage = userService.findAll(pageable);
    PagedResources<UserResource> resources = pagedResourcesAssembler.toResource(usersPage, assembler);
    return new ResponseEntity<>(resources, HttpStatus.OK);
  }

  @RequestMapping(path = "/{email}", method = RequestMethod.GET)
  public HttpEntity<UserResource> find(@PathVariable String email) {
    User user = userService.findOne(encodingService.decode(email));
    return new ResponseEntity<>(assembler.toResource(user), HttpStatus.OK);
  }

  @RequestMapping(path = "/{email}", method = RequestMethod.PUT)
  public HttpEntity<UserResource> update(@PathVariable String email, @RequestBody User user) {
    user.setEmail(encodingService.decode(email));
    UserResource userResource = assembler.toResource(userService.save(user));
    return new ResponseEntity<>(userResource, HttpStatus.OK);
  }

  @RequestMapping(path = "/{email}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable String email) {
    userService.deleteByEmail(encodingService.decode(email));
  }

}
