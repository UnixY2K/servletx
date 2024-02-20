package net.rnvn.servletx.routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Route {

    private String path;
    private ArrayList<Route> childRoutes = new ArrayList<>();

    public Route(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public List<Route> getChildRoutes() {
        return childRoutes;
    }

    public void addChild(Route route) {
        childRoutes.add(route);
    }

    public boolean checkMatchedLevels(String segment, String prependPath) {
        String[] pathSegments = (prependPath + "/" + path).split("/");
        String[] segmentSegments = segment.split("/");
        if (pathSegments.length != segmentSegments.length) {
            return false;
        }
        for (int i = 0; i < pathSegments.length; i++) {
            // for each segment we need to validate if our path contains a type inside the
            // URL segment, e.g. <id>
            if (pathSegments[i].startsWith("<") && pathSegments[i].endsWith(">")) {
                continue;
            }
            if (!pathSegments[i].equals(segmentSegments[i])) {
                return false;
            }
        }
        return true;
    }

    public Route getMatchingRoute(String urlSegment) {
        return getMatchingRoute(urlSegment, "");
    }

    public Route getMatchingRoute(String urlSegment, String prependPath) {
        // first check if the current URL matches with our URL
        if (checkMatchedLevels(urlSegment, prependPath)) {
            return this;
        }
        // if not check if it matches with any of our child routes
        for (Route route : childRoutes) {
            // match by our url segment and the child route's url segment
            if (route.getMatchingRoute(urlSegment, prependPath + path) != null) {
                return route;
            }
        }

        return null;
    }

    public static Map<String, String> getURLVariables(String path, String urlSegment) {
        HashMap<String, String> urlVariables = new HashMap<>();
        // first we need to split the URL segment into its parts
        String[] urlSegments = urlSegment.split("/");
        // if the path starts with a / we need to remove it
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        String[] pathSegments = path.split("/");
        // if the path is not the same length as the URL segment, we can't match
        if (urlSegments.length != pathSegments.length) {
            return urlVariables;
        }
        // iterate over the segments
        for (int i = 0; i < urlSegments.length; i++) {
            // if the segment is a type, e.g. <id>
            if (urlSegments[i].startsWith("<") && urlSegments[i].endsWith(">")) {
                // remove the < and > from the segment
                String type = urlSegments[i].substring(1, urlSegments[i].length() - 1);
                // add the type and the value to the URL params
                urlVariables.put(type, pathSegments[i]);
            }
        }
        return urlVariables;
    }

}
