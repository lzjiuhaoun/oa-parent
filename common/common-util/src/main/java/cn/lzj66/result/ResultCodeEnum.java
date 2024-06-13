package cn.lzj66.result;

import lombok.Getter;

/**
 * ClassName: ResultCodeEnum
 * Package: cn.lzj66.result
 * Description:统一返回结果状态信息类
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/3 15:36
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),

    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限"),
    LOGIN_MOBLE_ERROR(2018,"认证失败")
    ;

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}