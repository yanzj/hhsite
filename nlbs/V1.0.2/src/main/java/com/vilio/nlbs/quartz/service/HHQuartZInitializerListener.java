package com.vilio.nlbs.quartz.service;

import org.quartz.SchedulerException;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * Created by xiezhilei on 2017/3/8.
 */
public class HHQuartZInitializerListener extends QuartzInitializerListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // TODO Auto-generated method stub
        super.contextInitialized(sce);
        ServletContext sc=sce.getServletContext();
        StdSchedulerFactory fac=(StdSchedulerFactory) sc.getAttribute(QUARTZ_FACTORY_KEY);
        try {
            fac.getScheduler().getContext().put("servletContext", sc);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
