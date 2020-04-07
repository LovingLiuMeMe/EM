package cn.lovingliu.controller.equipmentManager;

import cn.lovingliu.controller.BaseController;
import cn.lovingliu.page.PagedGridResult;
import cn.lovingliu.pojo.User;
import cn.lovingliu.response.ServerResponse;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author：LovingLiu
 * @Description: 器材管理员
 * @Date：Created in 2020-03-08
 */
@Api(value = "器材管理员用户",tags = "器材管理员用户接口")
@RestController
@RequestMapping("em")
@Slf4j
public class EMController implements BaseController  {
    @Autowired
    private UserService userService;

    @Autowired
    private RecordService recordService;


    @ApiOperation(value = "获取器材管理员信息",notes = "获取器材管理员信息",httpMethod = "GET")
    @GetMapping(value = "/info")
    public ServerResponse<User> info(HttpServletRequest request){
        String valueInCookie = CookieUtil.get(request,USER_COOKIE_KEY);
        User userInCookie = JsonUtils.jsonToPojo(valueInCookie,User.class);
        User selectUser = userService.getUserById(userInCookie.getId());
        selectUser.setPassword(null);
        return ServerResponse.createBySuccess("获取成功",selectUser);
    }

    @ApiOperation(value = "修改器材管理员信息",notes = "修改器材管理员信息",httpMethod = "POST")
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

    @ApiOperation(value = "根据记录状态查询列表",notes = "根据记录状态查询列表",httpMethod = "POST")
    @GetMapping("/record/list")
    public ServerResponse recordList(
            @ApiParam(name = "recordStatus",value = "借还记录状态",required = false)
            @RequestParam(value = "recordStatus",defaultValue = "0",required = false) Integer recordStatus,

            @ApiParam(name = "page",value = "当前页码",required = false)
            @RequestParam(value = "page",defaultValue = "1") Integer page,

            @ApiParam(name = "pageSize",value = "每页显示的数量",required = false)
            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){

        PagedGridResult pagedGridResult = recordService.recordList(recordStatus,page,pageSize);
        return ServerResponse.createBySuccess(pagedGridResult);
    }

    @ApiOperation(value = "查看借还记录",notes = "查看借还记录",httpMethod = "GET")
    @GetMapping("/record/info/{recordId}")
    public ServerResponse recordInfo(@PathVariable Integer recordId){

        return recordService.recordInfo(recordId);
    }

    @ApiOperation(value = "审核借还记录",notes = "审核借还记录",httpMethod = "POST")
    @PostMapping("/record/check")
    public ServerResponse recordCheck(
            @ApiParam(name = "recordId",value = "审核借还记录ID",required = true)
            @RequestParam(value = "recordId") Integer recordId,
            @ApiParam(name = "checkPass",value = "是否通过审核",required = true)
            @RequestParam(value = "checkPass",defaultValue = "true") Boolean checkPass){
        return recordService.checkedRecord(recordId,checkPass);
    }

    @ApiOperation(value = "将学生/教师 加入/移除黑名单",notes = "将学生/教师 加入/移除黑名单",httpMethod = "POST")
    @PostMapping("/user/blacklist")
    public ServerResponse userBlackList(
            @ApiParam(name = "userId",value = "审核借还记录ID",required = true)
            @RequestParam(value = "userId") Integer userId){
        return userService.blacklist(userId);
    }
}
