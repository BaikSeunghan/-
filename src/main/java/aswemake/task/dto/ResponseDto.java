package aswemake.task.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  //null인 데이터는 json 결과에 나오지 않음
public class ResponseDto {

    private boolean response;
    private String message;
    private String product_name;

    private String price;

    public ResponseDto(boolean response, String message) {
        this.response = response;
        this.message = message;
    }
    public ResponseDto(boolean response, String product_name, String price) {
        this.response = response;
        this.product_name = product_name;
        this.price = price;
    }
}
