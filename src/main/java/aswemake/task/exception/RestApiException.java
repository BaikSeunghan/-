package aswemake.task.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestApiException {
    private String message;
    private Boolean response;
}