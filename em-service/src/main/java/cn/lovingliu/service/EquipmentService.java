package cn.lovingliu.service;

import cn.lovingliu.page.PagedGridResult;
import cn.lovingliu.pojo.Equipment;
import cn.lovingliu.pojo.bo.EquipmentBO;
import cn.lovingliu.response.ServerResponse;

public interface EquipmentService {
    PagedGridResult equipmentList(Integer equipmentStatus,Integer page,Integer pageSize);
    Equipment equipmentInfo(Integer equipmentId);
    ServerResponse changeEquipmentStatus(Integer equipmentId, Integer willChangeStatus);
    int createNewEquipment(EquipmentBO equipmentBO);
    int changeEquipmentCount(Integer equipmentId,Integer willOrderCount);
    ServerResponse changeEquipmentInfo(Equipment equipment);
}
