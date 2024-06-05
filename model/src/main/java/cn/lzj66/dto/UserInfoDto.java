package cn.lzj66.dto;

import cn.lzj66.vo.system.RouterVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: UserInfoDto
 * Package: cn.lzj66.dto
 * Description:
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/5 12:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String roles;
    private String name;
    private String avatar;
    private List<RouterVo> routers;
    private List<String> buttons;
}
