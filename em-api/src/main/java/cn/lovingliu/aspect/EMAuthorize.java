package cn.lovingliu.aspect;


import cn.lovingliu.constant.UserType;
import cn.lovingliu.controller.BaseController;
import cn.lovingliu.enums.ExceptionCodeEnum;
import cn.lovingliu.exception.EMException;
import cn.lovingliu.pojo.User;
import cn.lovingliu.util.CookieUtil;
import cn.lovingliu.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author：LovingLiu
 * @Description: 权限拦截
 * @Date：Created in 2019-11-25
 */
@Aspect
@Component
@Slf4j
public class EMAuthorize {

    /**
     * 学生/教师拦截器
     */
    @Pointcut("execution(public * cn.lovingliu.controller.user.*.* (..))")
    public void userVerify(){}
    @Before("userVerify()")
    public void doUserVerify() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userStrInCookie = CookieUtil.get(request, BaseController.USER_COOKIE_KEY);
        // 1.判断是否登录
        if(userStrInCookie == null){
            throw new EMException(ExceptionCodeEnum.AUTHORIZE_NOLOGIN_FAIL);
        }
        // 2.判断权限是否足够
        User userInCookie = JsonUtils.jsonToPojo(userStrInCookie, User.class);
        if(userInCookie.getType() != UserType.STUDENT && userInCookie.getType() != UserType.TEACHER){
            throw  new EMException(ExceptionCodeEnum.AUTHORIZE_ROLE_FAIL);
        }
    }

    /**
     * 器材管理员拦截器
     */
    @Pointcut("execution(public * cn.lovingliu.controller.equipmentManager.*.* (..))")
    public void EMVerify(){}
    @Before("EMVerify()")
    public void doEMVerify() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userStrInCookie = CookieUtil.get(request, BaseController.USER_COOKIE_KEY);
        // 1.判断是否登录
        if(userStrInCookie == null){
            throw new EMException(ExceptionCodeEnum.AUTHORIZE_NOLOGIN_FAIL);
        }
        // 2.判断权限是否足够
        User userInCookie = JsonUtils.jsonToPojo(userStrInCookie, User.class);
        if(userInCookie.getType() !=  UserType.MANAGER){
            throw  new EMException(ExceptionCodeEnum.AUTHORIZE_ROLE_FAIL);
        }
    }

    /**
     * 器材负责人拦截器
     */
    @Pointcut("execution(public * cn.lovingliu.controller.equipmentHeader.*.* (..))")
    public void EHVerify(){}
    @Before("EHVerify()")
    public void doEHVerify() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userStrInCookie = CookieUtil.get(request, BaseController.USER_COOKIE_KEY);
        // 1.判断是否登录
        if(userStrInCookie == null){
            throw new EMException(ExceptionCodeEnum.AUTHORIZE_NOLOGIN_FAIL);
        }
        // 2.判断权限是否足够
        User userInCookie = JsonUtils.jsonToPojo(userStrInCookie, User.class);
        if(userInCookie.getType() !=  UserType.HEADER){
            throw  new EMException(ExceptionCodeEnum.AUTHORIZE_ROLE_FAIL);
        }
    }
}
