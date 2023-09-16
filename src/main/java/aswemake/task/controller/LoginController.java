package aswemake.task.controller;

import aswemake.task.dto.SignUpRequestDto;
import aswemake.task.model.User;
import aswemake.task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

  private final UserRepository userRepository;
  @GetMapping("/accessDenied")
  public String accessDenied() {
    return "accessDenied";
  }

  @GetMapping("/login")
  public String loginForm() {
    return "login/loginForm";
  }

  @PostMapping("/loginProcess")
  public String login(SignUpRequestDto dto, Model model) {

    Optional<User> findUser = userRepository.findByName(dto.getName());
    log.info("findUser:" + findUser);

    if (findUser.isPresent()) {
      return "hello";
    }
    model.addAttribute("loginError", "존재하지않는 유저입니다.");
    return "login/loginForm";
  }

  @GetMapping(value = "/login/error")
  public String loginError(Model model) {
    model.addAttribute("loginError", "로그인 중 에러 발생.");
    return "login/loginForm";
  }

}
