package com.bamboo.common.config;


import com.bamboo.common.exception.GlobException;
import com.bamboo.common.http.request.MultiReadHttpServletRequest;
import com.bamboo.common.util.IoTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

//import javax.servlet.annotation.WebFilter;

/**
 * 日志拦截器:打印请求url和参数
 *
 * Created by lifh on 16/8/19.
 */
@Order(2)
@WebFilter(filterName = "logFilter", urlPatterns = "/*")
public class LogFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(LogFilter.class);

    private static final ThreadLocal<Long> startTimeThreadLocal =
            new NamedThreadLocal<Long>("ThreadLocal StartTime");
    private static final ThreadLocal<String> sysLogThreadUri =
            new NamedThreadLocal<String>("ThreadLocal URL");
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //1、开始时间
        startTimeThreadLocal.set( System.currentTimeMillis());//线程绑定变量（该数据只有当前请求的线程可见）


        MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest((HttpServletRequest) servletRequest);
        //打印日志
        printLog(multiReadRequest);

        //捕获你抛出的业务异常
        try {
            filterChain.doFilter(multiReadRequest, servletResponse);
        } catch (RuntimeException e) {
            if(e instanceof GlobException){//如果是你定义的业务异常
                log.info("GlobException:{}",e.toString());
            }
            e.printStackTrace();
        }
        logExeTime();

    }

    //打印日志
    private String printLog(MultiReadHttpServletRequest request) throws IOException {
        String uri = request.getRequestURI();
        String queryString = getQueryString(request);
        String body = requestBody(request);
        if (!StringUtils.isEmpty(body) && body.length() > 1024){
            body=body.substring(0,512)+"..."+body.substring(body.length()-50,body.length());
        }
        String params = StringUtils.isEmpty(body) ? queryString : body;
        log.info("request:token={},method={},url={}", request.getHeader("Authorization"),  request.getMethod(),uri + (params != null ? "?" + params : ""));
        sysLogThreadUri.set(  request.getMethod()+":"+uri);//线程绑定变量（该数据只有当前请求的线程可见）
        return params;
    }

    private String getQueryString(MultiReadHttpServletRequest request) {
        StringBuilder queryString = new StringBuilder();
        Map<String, String[]> map = request.getParameterMap();
        if (map == null || map.isEmpty()) return null;
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            queryString
                    .append(entry.getKey())
                    .append("=")
                    .append(IoTools.toString(entry.getValue()))
                    .append("&");
        }
        return queryString.length() > 0 ? queryString.substring(0, queryString.length() - 1) : null;
    }

    //获取body参数
    private String requestBody(MultiReadHttpServletRequest request) throws IOException {
        byte[] bytes = IoTools.readBytes(request.getInputStream());
        return new String(bytes, "UTF-8");
    }

    private void logExeTime(){
        Long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
        if(beginTime==null){
            beginTime=System.currentTimeMillis();
        }

        long endTime = System.currentTimeMillis(); 	//2、结束时间
        double executeTime =(double)(endTime - beginTime)/1000;//秒

        log.info("runtime/s\t{}\t{}",executeTime,sysLogThreadUri.get());
        //删除线程变量中的数据，防止内存泄漏
        startTimeThreadLocal.remove();
        sysLogThreadUri.remove();
    }

    //初始化读取你配置的提示页面路径
    public void init(FilterConfig config) throws ServletException {

    }
    public void destroy() {

    }




}
