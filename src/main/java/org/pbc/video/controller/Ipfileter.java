package org.pbc.video.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

public class Ipfileter implements Filter {

    protected FilterConfig config;
    protected String IP;

    //初始化操作。
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        //读取需要被限制的IP信息。
        IP = config.getInitParameter("IP");

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //读取客户端IP信息
        String remoteIP = null;
        HttpServletRequest request1 = (HttpServletRequest)request;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request1.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request1.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request1.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request1.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request1.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            remoteIP = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (remoteIP == null || remoteIP.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            remoteIP = request.getRemoteAddr();
        }

        System.out.println(remoteIP);
        //判断客户端IP是否与被限制IP匹配
        if(remoteIP.equals(IP)) {
            System.out.println("拦截");
            response.setCharacterEncoding("gb2312");
            PrintWriter out = response.getWriter();
            out.print("<p style='text-align:center; font-size:25'>IP限制！</p>");
        }else {
            //通过IP验证，请求转发下一级
            chain.doFilter(request, response);
        }
    }

    public void destroy() {

    }
}