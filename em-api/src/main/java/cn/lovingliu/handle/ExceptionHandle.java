package cn.lovingliu.handle;

import cn.lovingliu.exception.EMException;
import cn.lovingliu.response.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class ExceptionHandle {
    @ExceptionHandler(value = EMException.class)
    @ResponseBody
    public ServerResponse resolveException(Exception e){
        if(e instanceof EMException){
            EMException lovingMallException = (EMException) e;
            return ServerResponse.createByErrorStatusMessage(lovingMallException.getCode(),lovingMallException.getMessage());
        }
        return ServerResponse.createByErrorMessage(e.getMessage());
    }

    // 上传文件超过500K,捕获异常: MaxUploadSizeExceededException
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ServerResponse handleMaxUploadFile(MaxUploadSizeExceededException exception){
        return ServerResponse.createByErrorMessage("上传文件的大小不能超过500K,请压缩图片或者降低图片质量");
    }
}
