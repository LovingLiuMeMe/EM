package cn.lovingliu.service.impl;

import cn.lovingliu.constant.EquipmentStatus;
import cn.lovingliu.mapper.EquipmentMapper;
import cn.lovingliu.page.PagedGridResult;
import cn.lovingliu.pojo.Equipment;
import cn.lovingliu.pojo.bo.EquipmentBO;
import cn.lovingliu.response.ServerResponse;
import cn.lovingliu.service.EquipmentService;
import cn.lovingliu.service.common.BaseService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EquipmentServiceImpl extends BaseService implements EquipmentService{

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Override
    public PagedGridResult equipmentList(Integer equipmentStatus, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Equipment> recordList = equipmentMapper.selectListByStatus(equipmentStatus);
        return setterPagedGrid(recordList,page);
    }

    @Override
    public Equipment equipmentInfo(Integer equipmentId) {
        return equipmentMapper.selectByPrimaryKey(equipmentId);
    }

    @Override
    public ServerResponse changeEquipmentStatus(Integer equipmentId, Integer willChangeStatus) {
        Equipment equipmentInDB = equipmentMapper.selectByPrimaryKey(equipmentId);
        if(equipmentInDB.getStatus() == EquipmentStatus.INVALID){
            return ServerResponse.createByErrorMessage("该器材已经报废,不能再使用！");
        }
        Equipment equipment = new Equipment();
        equipment.setId(equipmentId);
        equipment.setStatus(willChangeStatus);
        int count = equipmentMapper.updateByPrimaryKeySelective(equipment);
        if(count > 0){
            return ServerResponse.createBySuccessMessage("更新状态成功");
        }else {
            return ServerResponse.createByErrorMessage("更新状态失败");
        }
    }

    @Override
    public int createNewEquipment(EquipmentBO equipmentBO) {
        Equipment equipment = new Equipment();
        BeanUtils.copyProperties(equipmentBO,equipment);
        equipment.setCreatedTime(new Date());
        equipment.setUpdatedTime(new Date());

        return equipmentMapper.insertSelective(equipment);
    }

    @Override
    public int changeEquipmentCount(Integer equipmentId, Integer willOrderCount) {
        return equipmentMapper.increaseCount(equipmentId,willOrderCount);
    }

    @Override
    public ServerResponse changeEquipmentInfo(Equipment equipment) {
        equipment.setStatus(null); // 修改商品信息 不可修改状态
        equipment.setAmount(null); // 修改商品信息 不可修改库存
        equipment.setCreatedTime(null); // 修改商品信息 不可修改创建时间
        equipment.setUpdatedTime(new Date());

        Equipment equipmentInDb = new Equipment();
        BeanUtils.copyProperties(equipment,equipmentInDb);

        int count = equipmentMapper.updateByPrimaryKeySelective(equipmentInDb);
        if(count > 0){
            return ServerResponse.createBySuccessMessage("修改成功");
        }else {
            return ServerResponse.createByErrorMessage("修改失败");
        }
    }
}
