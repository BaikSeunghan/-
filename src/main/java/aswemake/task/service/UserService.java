package aswemake.task.service;

import aswemake.task.dto.ResponseDto;
import aswemake.task.dto.SignUpRequestDto;
import aswemake.task.enums.UserRole;
import aswemake.task.model.User;
import aswemake.task.repository.UserRepository;
import aswemake.task.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;

  /**
   * 회원 가입
   *
   * @param requestDto 회원가입 DTO
   * @return user_id
   */
  @Transactional
  public ResponseEntity<?> createUser(SignUpRequestDto requestDto, PasswordEncoder passwordEncoder) {

    String name = requestDto.getName();
    String password = passwordEncoder.encode(requestDto.getPassword());
    UserRole role = requestDto.getRole();
    User user = new User(name, password, role);

    userRepository.save(user);
    return ResponseEntity.ok(new ResponseDto(true, "회원가입 성공"));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByName(username).orElseThrow(
        () -> new NullPointerException("없는 회원입니다."));

    return new UserDetailsImpl(user);
  }
}
