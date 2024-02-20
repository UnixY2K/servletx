package net.rnvn.servletx.sample.controllers;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.rnvn.servletx.annotations.ControllerMapping;
import net.rnvn.servletx.controller.BaseController;
import net.rnvn.servletx.controller.ControllerRouter;
import net.rnvn.servletx.routing.Route;

@ControllerMapping("hello/{name}")
public class HelloController extends BaseController {
    @Override
    protected void onGet(HttpServletRequest req,
            HttpServletResponse resp) throws IOException {
        String url = getCurrentUri(req);
        Map<String, String> urlVariables = Route.getURLVariables(url,
                ControllerRouter.getControllerRoute(this.getClass()));
        // get <name> from the url
        String name = urlVariables.get("name");
        writeResponse(resp, "Hello " + name, HttpServletResponse.SC_OK);
    }
}
