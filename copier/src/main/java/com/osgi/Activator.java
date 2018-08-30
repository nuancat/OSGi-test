package com.osgi;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator{

    Server server = null;
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        server = new Server();
        ServerConnector http = new ServerConnector(server);
        http.setHost("localhost");
        http.setPort(8000);
        http.setIdleTimeout(30000);
        server.addConnector(http);
        server.setHandler(new TaskLoader());
        server.start();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        server.stop();
    }
}
