package cn.lzj66.common.handler;

import cn.lzj66.common.execption.Lzj66ExceptionHandler;
import cn.lzj66.result.Result;
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
    //或者在可能出现异常的地方，try-catch捕获自定义异常即可
    @ExceptionHandler(Lzj66ExceptionHandler.class)
    @ResponseBody
    public Result error(Lzj66ExceptionHandler e){
        e.printStackTrace();
        return Result.fail().message(e.getMessage()).code(e.getCode());
    }
}