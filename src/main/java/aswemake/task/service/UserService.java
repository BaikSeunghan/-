package aswemake.task.service;

import aswemake.task.dto.SignUpRequestDto;
import aswemake.task.enums.UserRole;
import aswemake.task.model.User;
import aswemake.task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * 회원 가입
   * @param requestDto 회원가입 DTO
   * @return user_id
   */
  @Transactional
  public long createUser(SignUpRequestDto requestDto) {

    String name = requestDto.getName();
    String password = passwordEncoder.encode(requestDto.getPassword());
    UserRole role = requestDto.getRole();
    User user = new User(name, password, role);

    userRepository.save(user);

    return user.getId();
  }

}
