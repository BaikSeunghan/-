package aswemake.task.controller;

import aswemake.task.dto.LoginRequestDto;;
import aswemake.task.dto.ResponseDto;
import aswemake.task.model.User;
import aswemake.task.repository.UserRepository;
import aswemake.task.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {
  private final UserRepository userRepository;
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto requestDto) {

    userService.loadUserByUsername(requestDto.getName());

    if (userRepository.findByName(requestDto.getName()).isPresent()) {
      return ResponseEntity.ok(new ResponseDto(true, "로그인 성공"));
    } else {
      throw new IllegalArgumentException("로그인 실패");
    }
  }

}
