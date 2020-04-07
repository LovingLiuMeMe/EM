package cn.lovingliu.controller.equipmentHeader;

import cn.lovingliu.controller.BaseController;
import cn.lovingliu.page.PagedGridResult;
import cn.lovingliu.pojo.Equipment;
import cn.lovingliu.pojo.User;
import cn.lovingliu.pojo.bo.EquipmentBO;
import cn.lovingliu.response.ServerResponse;
import cn.lovingliu.service.EquipmentService;
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

/**
 * @Author：LovingLiu
 * @Description: 器材负责人
 * @Date：Created in 2020-03-08
 */
@Api(value = "器材负责人用户",tags = "器材负责人用户接口")
@RestController
@RequestMapping("eh")
@Slf4j
public class EHController implements BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private EquipmentService equipmentService;


    @ApiOperation(value = "获取器材负责人信息",notes = "获取器材负责人信息",httpMethod = "GET")
    @GetMapping(value = "/info")
    public ServerResponse<User> info(HttpServletRequest request){
        String valueInCookie = CookieUtil.get(request,USER_COOKIE_KEY);
        User userInCookie = JsonUtils.jsonToPojo(valueInCookie,User.class);
        User selectUser = userService.getUserById(userInCookie.getId());
        selectUser.setPassword(null);
        return ServerResponse.createBySuccess("获取成功",selectUser);
    }

    @ApiOperation(value = "修改器材负责人信息",notes = "修改器材负责人信息",httpMethod = "POST")
    @PostMapping("/info/update")
    public ServerResponse infoUpdate(@ApiParam(name = "user",value = "要修改的负责人信息",required = true)
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

    @ApiOperation(value = "获取器材列表",notes = "获取器材列表",httpMethod = "GET")
    @GetMapping(value = "/equipment/list")
    public ServerResponse<PagedGridResult> equipmentList(@ApiParam(name = "equipmentStatus",value = "器材状态",required = false)
                                          @RequestParam(value = "equipmentStatus",defaultValue = "0",required = false) Integer equipmentStatus,

                                          @ApiParam(name = "page",value = "当前页码",required = false)
                                          @RequestParam(value = "page",defaultValue = "1") Integer page,

                                          @ApiParam(name = "pageSize",value = "每页显示的数量",required = false)
                                          @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        PagedGridResult pagedGridResult = equipmentService.equipmentList(equipmentStatus,page,pageSize);
        return ServerResponse.createBySuccess(pagedGridResult);
    }

    @ApiOperation(value = "获取器材详细信息",notes = "获取器材详细信息",httpMethod = "GET")
    @GetMapping(value = "/equipment/info/{equipmentId}")
    public ServerResponse equipmentInfo(@PathVariable() Integer equipmentId){

        Equipment equipment = equipmentService.equipmentInfo(equipmentId);
        return ServerResponse.createBySuccess(equipment);
    }

    @ApiOperation(value = "修改器材详情",notes = "修改器材详情",httpMethod = "POST")
    @PostMapping(value = "/equipment/info/update")
    public ServerResponse equipmentInfoUpdate(@ApiParam(name = "equipment",value = "新建的器材信息",required = true)
                                              @RequestBody Equipment equipment){
        if (equipment.getId() == null){
            return ServerResponse.createByErrorMessage("请指定器材,否则无法修改");
        }
        return equipmentService.changeEquipmentInfo(equipment);
    }

    @ApiOperation(value = "报修/报废/恢复正常 器材状态",notes = "报修/报废/恢复正常 器材状态",httpMethod = "POST")
    @PostMapping(value = "/equipment/changeStatus")
    public ServerResponse equipmentChangeStatus(@ApiParam(name = "equipmentId",value = "要修改的器材ID",required = true)
                                                @RequestParam(value = "equipmentId") Integer equipmentId,

                                                @ApiParam(name = "willChangeStatus",value = "将要改变的器材状态",required = true)
                                                @RequestParam(value = "willChangeStatus") Integer willChangeStatus){

        return equipmentService.changeEquipmentStatus(equipmentId,willChangeStatus);
    }

    @ApiOperation(value = "采购新的器材",notes = "采购新的器材",httpMethod = "POST")
    @GetMapping(value = "/equipment/create")
    public ServerResponse equipmentCreate(@ApiParam(name = "equipmentBO",value = "新建的器材信息",required = true)
                                          @RequestBody EquipmentBO equipmentBO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ServerResponse.createByErrorMessage(bindingResult.getFieldError().getDefaultMessage());
        }
        int count = equipmentService.createNewEquipment(equipmentBO);
        if (count > 0){
            return ServerResponse.createBySuccessMessage("采购成功");
        }else {
            return ServerResponse.createByErrorMessage("采购失败");
        }
    }

    @ApiOperation(value = "对已存在的商品进行采购",notes = "对已存在的商品进行采购",httpMethod = "POST")
    @PostMapping(value = "/equipment/order")
    public ServerResponse equipmentOrder(@ApiParam(name = "equipmentId",value = "要采购的器材ID",required = true)
                                                @RequestParam(value = "equipmentId") Integer equipmentId,

                                                @ApiParam(name = "willOrderCount",value = "将要采购的数量",required = true)
                                                @RequestParam(value = "willOrderCount") Integer willOrderCount){

        int count = equipmentService.changeEquipmentCount(equipmentId,willOrderCount);
        if (count > 0){
            return ServerResponse.createBySuccessMessage("采购成功");
        }else {
            return ServerResponse.createByErrorMessage("采购失败");
        }
    }
}
