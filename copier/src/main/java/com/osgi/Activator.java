package com.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import java.util.Properties;

public class Activator implements BundleActivator, ServiceListener {
    TaskLoader taskLoader = null;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("test");
        Properties ps = new Properties();
        //ps.load(new FileInputStream("src/main/resources/Config.properties"));
        // taskLoader = new TaskLoader(ps.getProperty("taskFilePath"));
        taskLoader = new TaskLoader("/Users/shamilbikchentaev/tasks.txt");
        taskLoader.setRunning(true);
        taskLoader.start();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        taskLoader.setRunning(false);
        bundleContext.addServiceListener(this);
    }

    @Override
    public void serviceChanged(ServiceEvent serviceEvent) {
        System.out.println("service changed");
    }
}
