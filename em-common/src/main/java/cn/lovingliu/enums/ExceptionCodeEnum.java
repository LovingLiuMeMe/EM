package cn.lovingliu.enums;

/**
 * @Author：LovingLiu
 * @Description: 异常的状态
 * @Date：Created in 2019-10-29
 */
public enum ExceptionCodeEnum implements CodeEnum {
    UPLOAD_FILE_TYPE_ERROR(10001,"图片格式不符合要求"),
    PARAM_ERROR(10002,"参数错误"),
    AUTHORIZE_NOLOGIN_FAIL(10003,"用户未登录"),
    AUTHORIZE_ROLE_FAIL(10004,"权限不足");

    private final int code;
    private final String msg;

    ExceptionCodeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
