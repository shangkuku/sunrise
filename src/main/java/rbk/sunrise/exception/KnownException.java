package rbk.sunrise.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class KnownException extends RuntimeException {
    protected String message;
    protected Throwable throwable;
    protected int error = -1;
}
