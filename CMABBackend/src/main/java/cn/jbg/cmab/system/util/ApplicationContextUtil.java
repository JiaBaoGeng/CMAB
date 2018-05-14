package cn.jbg.cmab.system.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 这个类取得上下文相关的数据
 * @author jbg
 */
public class ApplicationContextUtil {

    public static ThreadLocal<HttpSession> threadLocalSession = new ThreadLocal<HttpSession>(); //提供新开线程用

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpSession getSession() throws Exception{
        return getSession(true);
    }

    public static HttpSession getSession(boolean create) throws Exception{
        try {

            if(RequestContextHolder.getRequestAttributes()==null){
                return threadLocalSession.get();
            }

            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest  request =requestAttributes.getRequest();
            return request.getSession(create);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }


    public static Object getRequestAttribute(String name) {
        HttpServletRequest request = getRequest();
        return request.getAttribute(name);
    }

    public static Object getRequestParameter(String name) {
        HttpServletRequest request = getRequest();
        return request.getParameter(name);
    }

    public static Object getSessionAttribute(String name) throws Exception{
        HttpSession session = getSession();
        return session.getAttribute(name);
    }

    public static void setSessionAttribute(String name, Object attribute) throws Exception{
        HttpSession session = getSession();

        session.setAttribute(name, attribute);
    }

    /**
     * 如果新开线程，需要传入WEB线程的SESSION信息
     * @param session
     */
    public static void setThreadSession(HttpSession session){
        threadLocalSession.set(session);
    }

    /**
     * 删除线程信息，避免可能出现内存泄露
     */
    public static void clearThreadSession(){
        threadLocalSession.set(null);
    }


}