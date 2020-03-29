package cn.lovingliu.exception;

import cn.lovingliu.enums.ExceptionCodeEnum;
import lombok.Data;

/**
 * @Author：LovingLiu
 * @Description: LovingMallException 统一异常处理
 * @Date：Created in 2019-10-29
 */
@Data
public class EMException extends RuntimeException {
    private Integer code;

    public EMException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    public EMException(String message) {
        super(message);
        this.code = ExceptionCodeEnum.PARAM_ERROR.getCode();
    }
    public EMException(ExceptionCodeEnum exceptionCodeEnum) {
        super(exceptionCodeEnum.getMsg());
        this.code = exceptionCodeEnum.getCode();
    }
}
