package rbk.sunrise.configuration;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import rbk.sunrise.exception.KnownException;
import rbk.sunrise.exception.ResponseErrorMessage;

/**
 * 处理系统已知的异常
 */
@ControllerAdvice
@Order(1)
public class KnownExceptionHandler {


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

}
