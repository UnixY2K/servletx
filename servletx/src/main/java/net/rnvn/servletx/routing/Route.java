package net.rnvn.servletx.routing;

import java.util.HashMap;
import java.util.Map;

public class Route {

    private String path;

    public Route(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public boolean checkMatchedLevels(String segment, String prependPath) {
        String joinedPath = prependPath.length() > 0 ? prependPath + "/" + path : path;
        String[] pathSegments = (joinedPath).split("/");
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
