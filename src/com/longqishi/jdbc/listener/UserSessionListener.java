package com.longqishi.jdbc.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * TUserSessionListener
 *
 * @version 1.0
 */
public class UserSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

        ServletContext context=httpSessionEvent.getSession().getServletContext();
        Integer count=(Integer)context.getAttribute("count");
        if(count==null){
            count=0;
        }else{
            int co = count.intValue( );
            count= co+1;
        }
        System.out.println("当前用户人数："+count);
        context.setAttribute("count", count);//保存人数

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

        ServletContext context=httpSessionEvent.getSession().getServletContext();
        Integer count=(Integer)context.getAttribute("count");
        int co=count.intValue();
        count=co-1;
        context.setAttribute("count", count);
        System.out.println("当前用户人数："+count);

    }
}
