package aswemake.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

  @NotBlank(message = "이름을 입력해주세요.")
  private String name;

  private int price;

  @NotBlank(message = "쿠폰 적용 유무 Y/N")
  @JsonProperty("special_coupon")
  private String specialCoupon; // Y/N



}
