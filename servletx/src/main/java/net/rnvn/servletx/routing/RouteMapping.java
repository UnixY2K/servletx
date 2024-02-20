package net.rnvn.servletx.routing;

import java.util.ArrayList;

import net.rnvn.servletx.controller.BaseController;

public class RouteMapping {
    private Route route;
    private BaseController controller;
    private ArrayList<RouteMapping> childRoutes;

    public RouteMapping(Route route, BaseController controller) {
        this.route = route;
        this.controller = controller;
        this.childRoutes = new ArrayList<>();
    }

    public Route getRoute() {
        return route;
    }

    public BaseController getController() {
        return controller;
    }

    public ArrayList<RouteMapping> getChildRoutes() {
        return childRoutes;
    }

    public void addChild(RouteMapping routeMapping) {
        childRoutes.add(routeMapping);
    }

    public RouteMapping getMatchingRoute(String urlSegment, String prependPath) {
        if (route.checkMatchedLevels(urlSegment, prependPath)) {
            return this;
        }
        for (RouteMapping routeMapping : childRoutes) {
            RouteMapping matchingRoute = routeMapping.getMatchingRoute(urlSegment, prependPath + route.getPath());
            if (matchingRoute != null) {
                return matchingRoute;
            }
        }
        return null;
    }

}
