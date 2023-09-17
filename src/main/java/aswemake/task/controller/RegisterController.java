package aswemake.task.controller;

import aswemake.task.dto.SignUpRequestDto;
import aswemake.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegisterController {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  /**
   * 회원 가입
   * @param requestDto
   * @return
   */
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody @Valid SignUpRequestDto requestDto) {

    return userService.createUser(requestDto, passwordEncoder);
  }
}
