package rbk.sunrise.configuration;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;
import rbk.sunrise.exception.KnownException;
import rbk.sunrise.exception.ResponseErrorMessage;

/**
 * 用于异常处理和参数转换
 */
@ControllerAdvice
public class CommonControllerAdvice extends ResponseEntityExceptionHandler {


    /**
     * 系统碰到已知异常情况需要返回给前端
     * 仅把该类异常处理作为消息传递返回，仍然返回200
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(KnownException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseErrorMessage handleException(KnownException exception) {
        return ResponseErrorMessage.builder().message(exception.getMessage()).error(exception.getError()).build();
    }


    /**
     * 重写 mvc异常处理方法
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
     * 处理controller参数验证异常
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(ex.getMessage());
        Object body = ResponseErrorMessage.builder().message(errorMsg).build();
        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

}
