package cn.lovingliu.pojo;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable {
    private Integer id;

    private Integer equipmentId;

    private Integer userId;

    private Integer status;

    private Integer count;

    private Date createdTime;

    private Date updatedTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table em_record
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column em_record.id
     *
     * @return the value of em_record.id
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column em_record.id
     *
     * @param id the value for em_record.id
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column em_record.equipment_id
     *
     * @return the value of em_record.equipment_id
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    public Integer getEquipmentId() {
        return equipmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column em_record.equipment_id
     *
     * @param equipmentId the value for em_record.equipment_id
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column em_record.user_id
     *
     * @return the value of em_record.user_id
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column em_record.user_id
     *
     * @param userId the value for em_record.user_id
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column em_record.status
     *
     * @return the value of em_record.status
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column em_record.status
     *
     * @param status the value for em_record.status
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column em_record.count
     *
     * @return the value of em_record.count
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    public Integer getCount() {
        return count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column em_record.count
     *
     * @param count the value for em_record.count
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column em_record.created_time
     *
     * @return the value of em_record.created_time
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column em_record.created_time
     *
     * @param createdTime the value for em_record.created_time
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column em_record.updated_time
     *
     * @return the value of em_record.updated_time
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column em_record.updated_time
     *
     * @param updatedTime the value for em_record.updated_time
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table em_record
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", equipmentId=").append(equipmentId);
        sb.append(", userId=").append(userId);
        sb.append(", status=").append(status);
        sb.append(", count=").append(count);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}