package cn.lovingliu.service;

import cn.lovingliu.page.PagedGridResult;
import cn.lovingliu.pojo.Record;
import cn.lovingliu.pojo.bo.RecordBO;
import cn.lovingliu.pojo.vo.RecordVO;
import cn.lovingliu.response.ServerResponse;

import java.util.List;

public interface RecordService {
    ServerResponse borrowEquipment(RecordBO recordBO);
    ServerResponse changeRecordStatus(int recordId,int willChangeRecordStatus);
    ServerResponse<List<Record>> recordList(int userId);
    PagedGridResult recordList(int recordStatus, int page, int pageSize);
    ServerResponse<RecordVO> recordInfo(int recordId);
    ServerResponse<String> checkedRecord(int recordId,Boolean checkPass);
}
