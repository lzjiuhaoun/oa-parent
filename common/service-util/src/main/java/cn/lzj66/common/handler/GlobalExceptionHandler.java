package cn.lzj66.common.handler;

import cn.lzj66.common.execption.Lzj66ExceptionHandler;
import cn.lzj66.result.Result;
import cn.lzj66.result.ResultCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName: GlobalExceptionHandler
 * Package: cn.lzj66.common.handler
 * Description:全局异常处理类
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/3 18:16
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    //处理全局异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }
    //处理指定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        e.printStackTrace();
        return Result.fail().message("执行了特定异常处理");
    }
    //处理自定义异常
    //有错误时，抛出异常：throw new Lzj66ExceptionHandler(code,message); 或者捕获异常：try-catch
    //此时该异常会被拦截，并将自定义的错误信息封装为Result后返回
    @ExceptionHandler(Lzj66ExceptionHandler.class)
    @ResponseBody
    public Result error(Lzj66ExceptionHandler e){
        e.printStackTrace();
        return Result.fail().message(e.getMessage()).code(e.getCode());
    }

    /**
     * spring security异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e) throws AccessDeniedException {
        return Result.fail().code(209).message(ResultCodeEnum.PERMISSION.getMessage());
    }
}