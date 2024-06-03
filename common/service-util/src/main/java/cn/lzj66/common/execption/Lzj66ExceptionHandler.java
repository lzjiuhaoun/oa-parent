package cn.lzj66.common.execption;

import cn.lzj66.result.ResultCodeEnum;
import lombok.Data;

/**
 * ClassName: Lzj66ExceptionHandler
 * Package: cn.lzj66.common.execption
 * Description: 自定义异常类
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/3 18:21
 */
@Data
public class Lzj66ExceptionHandler extends RuntimeException {

    private Integer code;

    private String message;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param code
     * @param message
     */
    public Lzj66ExceptionHandler(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public Lzj66ExceptionHandler(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "GuliException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}