package aswemake.task.controller;

import aswemake.task.model.User;
import aswemake.task.repository.UserRepository;
import aswemake.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/v1/user")
@RestController
@RequiredArgsConstructor
public class TestController {
  private final UserService userService;
  private final UserRepository userRepository;

  @GetMapping(value = "/{id}")
  @PreAuthorize("hasRole('MART')")
  public User getUser(@PathVariable long id) {
    return userRepository.findById(id).orElseThrow(
        () -> new NullPointerException("없는 회원입니다."));
  }

}
