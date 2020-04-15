package cn.lovingliu.controller.user;

import cn.lovingliu.constant.EquipmentStatus;
import cn.lovingliu.constant.RecordStatus;
import cn.lovingliu.controller.BaseController;
import cn.lovingliu.exception.EMException;
import cn.lovingliu.page.PagedGridResult;
import cn.lovingliu.pojo.User;
import cn.lovingliu.pojo.bo.RecordBO;
import cn.lovingliu.response.ServerResponse;
import cn.lovingliu.service.EquipmentService;
import cn.lovingliu.service.RecordService;
import cn.lovingliu.service.UserService;
import cn.lovingliu.util.CookieUtil;
import cn.lovingliu.util.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(value = "教师/学生用户",tags = "教师/学生用户接口")
@RestController
@RequestMapping("user")
@Slf4j
public class UserController implements BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private EquipmentService equipmentService;


    @ApiOperation(value = "获取教师/学生信息",notes = "获取教师/学生信息",httpMethod = "GET")
    @GetMapping(value = "/info")
    public ServerResponse<User> info(HttpServletRequest request){
        String valueInCookie = CookieUtil.get(request,USER_COOKIE_KEY);
        User userInCookie = JsonUtils.jsonToPojo(valueInCookie,User.class);
        User selectUser = userService.getUserById(userInCookie.getId());
        selectUser.setPassword(null);
        return ServerResponse.createBySuccess("获取成功",selectUser);
    }

    @ApiOperation(value = "修改用户信息",notes = "修改用户信息",httpMethod = "POST")
    @PostMapping("/info/update")
    public ServerResponse infoUpdate(@ApiParam(name = "user",value = "要修改的管理员信息",required = true)
                                     @RequestBody User user,HttpServletRequest request){

        String valueInCookie = CookieUtil.get(request,USER_COOKIE_KEY);
        User userInCookie = JsonUtils.jsonToPojo(valueInCookie,User.class);
        if(StringUtils.isNotBlank(user.getTelephone())){
            User userInDb = userService.getUserByTelePhone(user.getTelephone());
            if(userInDb.getId() != userInCookie.getId()){
                return ServerResponse.createByErrorMessage("该号码已被使用!");
            }
        }
        user.setId(userInCookie.getId());
        int count = userService.updateByUser(user);
        if(count > 0){
            return ServerResponse.createBySuccessMessage("更新成功");
        }else {
            return ServerResponse.createByErrorMessage("更新失败");
        }
    }


    @ApiOperation(value = "借用器材",notes = "借用器材",httpMethod = "POST")
    @PostMapping("/equipment/borrow")
    public ServerResponse equipmentBorrow(@ApiParam(name = "recordBO",value = "借用详情",required = true)
                                          @RequestBody RecordBO recordBO, HttpServletRequest request, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new EMException(bindingResult.getFieldError().getDefaultMessage());
        }
        String valueInCookie = CookieUtil.get(request,USER_COOKIE_KEY);
        User userInCookie = JsonUtils.jsonToPojo(valueInCookie,User.class);
        recordBO.setUserId(userInCookie.getId());
        return recordService.borrowEquipment(recordBO);
    }

    @ApiOperation(value = "归还器材",notes = "归还器材",httpMethod = "POST")
    @PostMapping("/equipment/return")
    public ServerResponse equipmentReturn(@ApiParam(name = "recordId",value = "借记记录ID",required = true)
                                            @RequestParam(value = "recordId",required = true)
                                            Integer recordId){

        return recordService.changeRecordStatus(recordId, RecordStatus.WAIT_RETURN_CHECK);
    }

    @ApiOperation(value = "查看借还记录列表",notes = "查看借还记录列表",httpMethod = "POST")
    @GetMapping("/record/list")
    public ServerResponse recordList(HttpServletRequest request){
        String valueInCookie = CookieUtil.get(request,USER_COOKIE_KEY);
        User userInCookie = JsonUtils.jsonToPojo(valueInCookie,User.class);

        return recordService.recordList(userInCookie.getId());
    }

    @ApiOperation(value = "查看借还记录列表",notes = "查看借还记录列表",httpMethod = "GET")
    @GetMapping("/record/info/{recordId}")
    public ServerResponse recordInfo(@PathVariable Integer recordId){

        return recordService.recordInfo(recordId);
    }

    @ApiOperation(value = "处理异常借还记录",notes = "处理异常借还记录",httpMethod = "POST")
    @PostMapping("/record/deal")
    public ServerResponse dealUnusualRecord(@ApiParam(name = "recordId",value = "借记记录ID",required = true)
                                          @RequestParam(value = "recordId",required = true)
                                                  Integer recordId){
        return recordService.changeRecordStatus(recordId, RecordStatus.WAIT_DEAL_CHECK);
    }

    @ApiOperation(value = "获取器材列表",notes = "获取器材列表",httpMethod = "GET")
    @GetMapping(value = "/equipment/list")
    public ServerResponse<PagedGridResult> equipmentList(@ApiParam(name = "page",value = "当前页码",required = false)
                                                         @RequestParam(value = "page",defaultValue = "1") Integer page,

                                                         @ApiParam(name = "pageSize",value = "每页显示的数量",required = false)
                                                         @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        PagedGridResult pagedGridResult = equipmentService.equipmentList(EquipmentStatus.NORMAL,page,pageSize);
        return ServerResponse.createBySuccess(pagedGridResult);
    }
}
