package cn.lovingliu.service.impl;

import cn.lovingliu.constant.EquipmentStatus;
import cn.lovingliu.constant.RecordStatus;
import cn.lovingliu.mapper.EquipmentMapper;
import cn.lovingliu.mapper.RecordMapper;
import cn.lovingliu.mapper.UserMapper;
import cn.lovingliu.page.PagedGridResult;
import cn.lovingliu.pojo.Equipment;
import cn.lovingliu.pojo.Record;
import cn.lovingliu.pojo.User;
import cn.lovingliu.pojo.bo.RecordBO;
import cn.lovingliu.pojo.vo.RecordVO;
import cn.lovingliu.response.ServerResponse;
import cn.lovingliu.service.RecordService;
import cn.lovingliu.service.common.BaseService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RecordServiceImpl extends BaseService implements RecordService {
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse borrowEquipment(RecordBO recordBO) {
        int borrowCount = recordBO.getCount();
        int equipmentId = recordBO.getEquipmentId();
        Integer userId = recordBO.getUserId();
        User userInDb = userMapper.selectByPrimaryKey(userId);
        if(userInDb.getInBlacklist() == 1){
            return ServerResponse.createByErrorMessage("你已被拉入系统黑名单,无法借取器材!请联系管理员");
        }
        Equipment equipmentInDb = equipmentMapper.selectByPrimaryKey(equipmentId);
        if(equipmentInDb.getStatus() != EquipmentStatus.NORMAL){
            return ServerResponse.createByErrorMessage("该器材状态异常,无法借出");
        }
        int count = equipmentMapper.decreaseCount(equipmentId,borrowCount);

        if(count == 0){
            return ServerResponse.createByErrorMessage("库存不足,器材无法借出");
        }
        Record newRecord = new Record();
        BeanUtils.copyProperties(recordBO,newRecord);
        newRecord.setCreatedTime(new Date());
        newRecord.setUpdatedTime(new Date());
        int changeCount = recordMapper.insertSelective(newRecord);
        if(changeCount > 0){
            return ServerResponse.createBySuccessMessage("创建借记记录成功,等待管理员审核");
        }else {
            return ServerResponse.createByErrorMessage("创建借记记录失败,请联系管理员");
        }
    }

    @Override
    public ServerResponse changeRecordStatus(int recordId, int willChangeRecordStatus) {
        Record recordInDb = recordMapper.selectByPrimaryKey(recordId);

        if(willChangeRecordStatus == RecordStatus.WAIT_RETURN_CHECK){
            if(recordInDb.getStatus() != RecordStatus.WAIT_RETURN){
                return  ServerResponse.createByErrorMessage("借还记录状态异常,不能归还,请联系管理员");
            }
        }

        if(willChangeRecordStatus == RecordStatus.FINISAH){
            if(recordInDb.getStatus() != RecordStatus.WAIT_DEAL){
                return  ServerResponse.createByErrorMessage("借还记录状态异常,无法处理,请联系管理员");
            }
        }
        recordInDb.setUpdatedTime(new Date());
        recordInDb.setStatus(willChangeRecordStatus);
        recordMapper.updateByPrimaryKeySelective(recordInDb);
        return ServerResponse.createBySuccessMessage("更新借还记录状态成功");
    }

    @Override
    public ServerResponse<List<RecordVO>> recordList(int userId) {
        List<RecordVO> recordVOList = recordMapper.selectByUserId(userId);
        return ServerResponse.createBySuccess("获取成功",recordVOList);
    }

    @Override
    public ServerResponse<RecordVO> recordInfo(int recordId) {
        RecordVO recordVO = recordMapper.selectByPrimaryKeyToVO(recordId);
        return ServerResponse.createBySuccess("获取成功",recordVO);
    }

    @Override
    public PagedGridResult recordList(int recordStatus, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        List<RecordVO> recordList = recordMapper.selectListByStatus(recordStatus);
        return setterPagedGrid(recordList,page);
    }

    @Override
    public ServerResponse<String> checkedRecord(int recordId,Boolean checkPass) {
        Record recordInDb = recordMapper.selectByPrimaryKey(recordId);
        Integer status = recordInDb.getStatus();
        String msg = "借记记录状态异常,无法审核!";
        if(status == RecordStatus.WAIT_DECREASE_CHECK){
            /**
             * 审核借出请求
             */
            if(checkPass){
                // 通过
                recordInDb.setStatus(RecordStatus.WAIT_RETURN);
                recordMapper.updateByPrimaryKeySelective(recordInDb);
                msg = "审核通过,操作成功";
            }else {
                // 拒绝
                int count = recordInDb.getCount();
                equipmentMapper.increaseCount(recordInDb.getEquipmentId(),count);

                recordInDb.setStatus(RecordStatus.CHECK_NO_PASS);
                recordMapper.updateByPrimaryKeySelective(recordInDb);
                msg = "审核不通过,拒绝借出";
            }

            return ServerResponse.createBySuccessMessage(msg);
        }else if(status == RecordStatus.WAIT_RETURN_CHECK){
            /**
             * 审核归还请求
             */
            if(checkPass){
                // 通过
                int count = recordInDb.getCount();
                equipmentMapper.increaseCount(recordInDb.getEquipmentId(),count);

                recordInDb.setStatus(RecordStatus.FINISAH);
                recordMapper.updateByPrimaryKeySelective(recordInDb);
                msg = "审核通过,操作成功";
            }else {
                // 拒绝
                recordInDb.setStatus(RecordStatus.WAIT_DEAL);
                recordMapper.updateByPrimaryKeySelective(recordInDb);
                // 增加违章次数
                userMapper.increaseInfractionsCount(recordInDb.getUserId());
                msg = "审核不通过,等待用户处理";
            }
            return ServerResponse.createBySuccessMessage(msg);

        }else if(status == RecordStatus.WAIT_DEAL_CHECK){
            /**
             * 审核处理请求
             */
            if(checkPass){
                // 通过
                int count = recordInDb.getCount();
                equipmentMapper.increaseCount(recordInDb.getEquipmentId(),count);

                recordInDb.setStatus(RecordStatus.FINISAH);
                recordMapper.updateByPrimaryKeySelective(recordInDb);

                // 减少违章次数
                userMapper.decreaseInfractionsCount(recordInDb.getUserId());
                msg = "审核通过,操作成功";
            }else {
                // 拒绝
                msg = "审核不通过,处理未完成!";
            }
            return ServerResponse.createBySuccessMessage(msg);
        }else {
            return ServerResponse.createByErrorMessage(msg);
        }
    }
}
