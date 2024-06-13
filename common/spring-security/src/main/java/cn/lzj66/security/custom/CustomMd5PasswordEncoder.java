package cn.lzj66.security.custom;

import cn.lzj66.utils.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * ClassName: CustomMd5PasswordEncoder
 * Package: cn.lzj66.security.custom
 * Description: 自定义spring security自定义密码加密接口
 * 本项目采用MD5加密
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/13 11:13
 */
@Component
public class CustomMd5PasswordEncoder implements PasswordEncoder {

    public String encode(CharSequence rawPassword) {
        return MD5.encrypt(rawPassword.toString());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
    }
}
