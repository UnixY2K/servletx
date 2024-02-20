package net.rnvn.servletx.controller;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.rnvn.servletx.routing.Route;
import net.rnvn.servletx.routing.RouteHandler;

public class ControllerRouter extends HttpServlet {

    enum HttpMethod {
        GET, POST, PUT, DELETE
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        routeRequest(req, resp, HttpMethod.GET);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        routeRequest(req, resp, HttpMethod.POST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        routeRequest(req, resp, HttpMethod.PUT);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        routeRequest(req, resp, HttpMethod.DELETE);
    }

    private void routeRequest(HttpServletRequest req, HttpServletResponse resp, HttpMethod method) {
        // get the base path
        String path = req.getRequestURI().substring(req.getContextPath().length());
        // return the path as the response
        try {
            RouteHandler routeHandler = getRoutes();
            if (null != routeHandler) {
                Route route = routeHandler.getMatchingRoute(path);
                dispatchControllerRequest(req, resp, method, route);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dispatchControllerRequest(HttpServletRequest req, HttpServletResponse resp, HttpMethod method,
            Route route) {
        if (route == null) {
            on404(req, resp);
            return;
        }
        Map<String, BaseController> matchingController = getMatchingController();
        if (null != matchingController) {
            BaseController controller = matchingController.get(route.getPath());
            if (null != controller) {
                try {
                    switch (method) {
                        case GET:
                            controller.doGet(req, resp);
                            break;
                        case POST:
                            controller.doPost(req, resp);
                            break;
                        case PUT:
                            controller.doPut(req, resp);
                            break;
                        case DELETE:
                            controller.doDelete(req, resp);
                            break;
                        default:
                            on404(req, resp);
                            break;
                    }
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
            } else {
                on404(req, resp);
            }
        }
    }

    protected RouteHandler getRoutes() {
        return null;
    }

    protected Map<String, BaseController> getMatchingController() {
        return null;
    }

    protected void on404(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
