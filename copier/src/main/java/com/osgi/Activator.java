package com.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import java.io.File;
import java.util.Date;

public class Activator implements BundleActivator, ServiceListener {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("started");
        new File("/Users/shamilbikchentaev/" +
                new Date().getMinutes() + "." + new Date().getSeconds()).createNewFile();
        bundleContext.addServiceListener(this);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("stopped");
        bundleContext.addServiceListener(this);
    }

    @Override
    public void serviceChanged(ServiceEvent serviceEvent) {
        System.out.println("service changed");
    }
}
