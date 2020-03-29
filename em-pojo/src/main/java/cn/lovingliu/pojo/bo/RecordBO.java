package cn.lovingliu.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecordBO implements Serializable {

    private static final long serialVersionUID = -5896219365355953019L;

    private Integer id;

    @NotBlank(message = "器材ID不能为空")
    private Integer equipmentId;

    private Integer userId;

    private Integer status;
    @Min(value = 1,message = "借记数量必须大于1")
    private Integer count;
}