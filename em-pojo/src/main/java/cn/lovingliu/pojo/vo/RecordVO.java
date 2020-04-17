package cn.lovingliu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecordVO implements Serializable {

    private static final long serialVersionUID = -7669720513169135256L;

    private Integer id;

    private Integer equipmentId;

    private Integer userId;

    private Integer status;

    private Integer count;

    private String userName;

    private String userNumber;

    private String equipmentName;

    private Date createdTime;

    private Date updatedTime;
}
