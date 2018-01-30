package rbk.sunrise.configuration;

import lombok.extern.log4j.Log4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rbk.sunrise.exception.ResponseErrorMessage;


/**
 * 系统全局异常，接收所有可抛出的异常
 * 为了防止覆盖其他异常类型的处理，这个优先级要最低
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
@Log4j
public class GlobalExceptionHandler {

    /**
     * 系统发生未知的异常
     *
     * @param t
     * @return
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseErrorMessage handleException(Throwable t) {
        log.error("", t);
        return ResponseErrorMessage.builder().message(t.getMessage()).build();
    }

}
