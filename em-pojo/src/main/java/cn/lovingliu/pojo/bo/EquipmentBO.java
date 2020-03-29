package cn.lovingliu.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentBO implements Serializable {

    private Integer id;

    private String portrait;
    @NotBlank(message = "器材名称必填")
    private String name;

    private String introduction;

    private Integer amount;

    private Integer status;

    private String damageDescription;
}