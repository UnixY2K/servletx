package net.rnvn.servletx.sample;

import java.util.HashMap;
import java.util.Map;

import net.rnvn.servletx.controller.BaseController;
import net.rnvn.servletx.routing.Route;
import net.rnvn.servletx.routing.RouteHandler;

/**
 * singleton class that contains the RouteHandler
 */
public class RouteHandlerSingleton {

    private void initRoutes() {
        routeHandler.addRoute(new Route("hello/<name>"));
        controllers.put("hello/<name>", new HelloController());
    }

    private static RouteHandlerSingleton instance = null;

    private RouteHandler routeHandler = new RouteHandler();
    private Map<String, BaseController> controllers = new HashMap<>();

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

    public static Map<String, BaseController> getMatchingController() {
        return getInstance().controllers;
    }

}
