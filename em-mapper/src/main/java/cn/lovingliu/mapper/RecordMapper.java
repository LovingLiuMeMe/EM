package cn.lovingliu.mapper;

import cn.lovingliu.pojo.Record;
import cn.lovingliu.pojo.vo.RecordVO;

import java.util.List;

public interface RecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table em_record
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table em_record
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    int insert(Record record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table em_record
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    int insertSelective(Record record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table em_record
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    Record selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table em_record
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    int updateByPrimaryKeySelective(Record record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table em_record
     *
     * @mbg.generated Sun Mar 08 13:15:23 CST 2020
     */
    int updateByPrimaryKey(Record record);

    List<Record> selectByUserId(int userId);

    List<RecordVO> selectListByStatus(int status);

    RecordVO selectByPrimaryKeyToVO(Integer id);
}