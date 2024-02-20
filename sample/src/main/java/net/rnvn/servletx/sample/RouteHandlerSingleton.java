package net.rnvn.servletx.sample;

import net.rnvn.servletx.routing.RouteHandler;
import net.rnvn.servletx.sample.controllers.HelloController;

/**
 * singleton class that contains the RouteHandler
 */
public class RouteHandlerSingleton {

    private void initRoutes() {
        routeHandler.addRoute(new HelloController());
    }

    private static RouteHandlerSingleton instance = null;

    private RouteHandler routeHandler = new RouteHandler();

    private RouteHandlerSingleton() {
        initRoutes();
    }

    public static RouteHandlerSingleton getInstance() {
        if (instance == null) {
            instance = new RouteHandlerSingleton();
        }
        return instance;
    }

    public static RouteHandler getRouteHandler() {
        return getInstance().routeHandler;
    }

}
