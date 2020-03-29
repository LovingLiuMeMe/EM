package cn.lovingliu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：LovingLiu
 * @Description: Cookie工具类
 * @Date：Created in 2019-10-30
 */
public class CookieUtil {
    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);
    public static void set(HttpServletResponse response,
                           String key,
                           String value,
                           int maxAge){
        try {
            value = URLEncoder.encode(value,"UTF-8");
            Cookie cookie = new Cookie(key,value);
            cookie.setPath("/");
            cookie.setMaxAge(maxAge);
            response.addCookie(cookie);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

    }
    public static String get(HttpServletRequest request, String key){
        try {
            Map<String, Cookie> cookieMap =readCookieMap(request);
            Cookie cookie = null;
            if(cookieMap.containsKey(key)){
                cookie = cookieMap.get(key);
            }
            if (cookie == null){
                return null;
            }
            return URLDecoder.decode(cookie.getValue(),"UTF-8");
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request){
        Cookie[] cookies = request.getCookies() == null ? new Cookie[0] : request.getCookies() ;
        Map<String, Cookie> cookieMap = new HashMap<>();
        for (Cookie cookie:cookies) {
            String name = cookie.getName();
            cookieMap.put(name,cookie);
        }
        return cookieMap;
    }
}
