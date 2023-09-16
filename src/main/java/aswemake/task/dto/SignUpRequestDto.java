package aswemake.task.dto;

import aswemake.task.enums.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class SignUpRequestDto {

  @NotBlank(message = "이름을 입력해주세요.")
  private String name;

  private String password;

//  @NotBlank(message = "역할을 입력해주세요.")
  private UserRole role;

}
