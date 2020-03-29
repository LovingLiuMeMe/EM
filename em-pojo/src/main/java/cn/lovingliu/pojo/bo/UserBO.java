package cn.lovingliu.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserBO implements Serializable {
    private static final long serialVersionUID = 6501119964046294370L;

    private Integer id;
    @NotBlank(message = "编号不能为空")
    private String number;

    private String name;

    private Integer gender;

    private Integer type;
    @NotBlank(message = "电话号码不能为空")
    private String telephone;
    @NotBlank(message = "登录密码不能为空")
    private String password;

    private String college;

    private String major;

    private Date createdTime;

    private Date updatedTime;
}