package rbk.sunrise.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorMessage {

    /**
     * 错误代码 -1为默认
     */
    protected int error = -1;

    /**
     * 消息内容
     */
    protected String message;

    /**
     * 数据
     */
    protected Object data;

}
