package aswemake.task.controller;

import aswemake.task.dto.SignUpRequestDto;
import aswemake.task.enums.UserRole;
import aswemake.task.model.User;
import aswemake.task.security.UserDetailsImpl;
import aswemake.task.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.zip.DataFormatException;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public String create(@RequestBody @Valid SignUpRequestDto requestDto, Model model){

    model.addAttribute("user", userService.createUser(requestDto));
    return "signup";
//    return "redirect:/";
  }

  @GetMapping("/hello")
  public String hello(Model model) {
    model.addAttribute("user", "let's go");
    return "hello";
  }
}
