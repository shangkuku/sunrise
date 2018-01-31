package rbk.sunrise.configuration;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rbk.sunrise.exception.ResponseErrorMessage;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 处理spring mvc的异常
 */
@ControllerAdvice
@Order(1)
public class MvcExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 重写 mvc异常处理方法
     *
     * @param ex
     * @param body
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        body = body == null ? ResponseErrorMessage.builder().message(ex.getMessage()).build() : body;
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }


    /**
     * 处理controller @RequestBody 参数验证异常
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + fieldError.getDefaultMessage())
                .findFirst()
                .orElse(ex.getMessage());
        Object body = ResponseErrorMessage.builder().message(errorMsg).build();
        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    /**
     * @RequestParam的变量验证时会抛出 ConstraintViolationException，跟@RequestBody的不一样。
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseErrorMessage handleConstraintViolationException(ConstraintViolationException e) {
        // validation的注解中message必须要添加完整的错误信息（包括参数名称）
        String errorMsg = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse(e.getMessage());
        return ResponseErrorMessage.builder().message(errorMsg).build();
    }

}
