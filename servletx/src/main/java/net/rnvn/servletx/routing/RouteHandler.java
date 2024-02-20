package net.rnvn.servletx.routing;

import java.util.ArrayList;
import java.util.List;

import net.rnvn.servletx.controller.BaseController;
import net.rnvn.servletx.controller.ControllerRouter;

public class RouteHandler {

    List<RouteMapping> routes = new ArrayList<>();

    public void addRoute(Route route, BaseController controller) {
        routes.add(new RouteMapping(route, controller));
    }

    public void addRoute(BaseController controller) {
        Route route = new Route(ControllerRouter.getControllerRoute(controller.getClass()));
        routes.add(new RouteMapping(route, controller));
    }

    public void addRouteMapping(RouteMapping routeMapping) {
        routes.add(routeMapping);
    }

    public RouteMapping getFirstMatchingRouteMapping(String path) {
        // if the path begins with a slash, remove it
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        for (RouteMapping routeMapping : routes) {
            RouteMapping matchingRoute = routeMapping.getMatchingRoute(path, "");
            if (matchingRoute != null) {
                return matchingRoute;
            }
        }
        return null;
    }

    public BaseController getFirstMatchingController(String path) {
        RouteMapping routeMapping = getFirstMatchingRouteMapping(path);
        return routeMapping != null ? routeMapping.getController() : null;
    }

}
