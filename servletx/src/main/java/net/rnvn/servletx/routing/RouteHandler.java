package net.rnvn.servletx.routing;

import java.util.ArrayList;

public class RouteHandler {

    ArrayList<Route> routes = new ArrayList<>();

    public RouteHandler() {
    }

    ArrayList<Route> getRoutes() {
        return routes;
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public void addRoutes(ArrayList<Route> routes) {
        this.routes.addAll(routes);
    }

    public Route getMatchingRoute(String url) {
        for (Route route : routes) {
            Route matchedRoute = route.getMatchingRoute(url);
            if (matchedRoute != null) {
                return matchedRoute;
            }
        }
        return null;
    }

}
