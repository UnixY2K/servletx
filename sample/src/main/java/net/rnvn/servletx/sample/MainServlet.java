package net.rnvn.servletx.sample;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.rnvn.servletx.controller.ControllerRouter;
import net.rnvn.servletx.routing.RouteHandler;

@WebServlet(name = "RouteController", urlPatterns = { "/" })
public class MainServlet extends ControllerRouter {

    @Override
    protected RouteHandler getRouteHandler() {
        return RouteHandlerSingleton.getRouteHandler();
    }

    @Override
    protected void on404(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.getWriter().write("404");
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
