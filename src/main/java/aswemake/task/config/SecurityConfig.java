package aswemake.task.config;

import aswemake.task.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsServiceImpl userDetailsService;
  private final CorsConfig corsConfig;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

//    http
//        .authorizeRequests()
//            .antMatchers("/user/signup").permitAll()
//            .antMatchers("/login").permitAll()
//    //        .antMatchers("/", "/home", "/login", "/user/signUp").permitAll() // 설정한 리소스의 접근을 인증절차 없이 허용
//            .antMatchers("/mart").hasRole(UserRole.MART.toString()) // MART 역할을 가지고 있어야 접근 허용
//            .anyRequest().authenticated() // 그 외 모든 리소스를 의미하며 인증 필요
//            .and()
//        .formLogin()
//            .permitAll()
//            .loginPage("/user/sign") // 기본 로그인 페이지
//            .and()
//        .logout()
//            .permitAll()
//            // .logoutUrl("/logout") // 로그아웃 URL (기본 값 : /logout)
//            // .logoutSuccessUrl("/login?logout") // 로그아웃 성공 URL (기본 값 : "/login?logout")
//            .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 주소창에 요청해도 포스트로 인식하여 로그아웃
//            .deleteCookies("JSESSIONID") // 로그아웃 시 JSESSIONID 제거
//            .invalidateHttpSession(true) // 로그아웃 시 세션 종료
//            .clearAuthentication(true) // 로그아웃 시 권한 제거
//            .and()
//            .exceptionHandling()
//            .accessDeniedPage("/accessDenied");

    http.csrf().disable();
    http.cors();
    http
        .addFilterBefore(corsConfig.corsFilter(), SecurityContextPersistenceFilter.class);

    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/loginProcess")
            .usernameParameter("name")
            .defaultSuccessUrl("/user/hello")
            .permitAll()
            .failureUrl("/login/error")
        .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true);

    http.authorizeRequests()
            .antMatchers("/admin/**", "/adminitem/**", "user/hello").hasAuthority("MART")
            .antMatchers("/", "/login/**", "/register", "/registerProcess").permitAll()
            .anyRequest().authenticated();
  }

  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Bean
  public static BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
  }

}
