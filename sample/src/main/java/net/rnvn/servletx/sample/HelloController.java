package net.rnvn.servletx.sample;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.rnvn.servletx.controller.BaseController;
import net.rnvn.servletx.routing.Route;

public class HelloController extends BaseController {
    @Override
    protected void onGet(HttpServletRequest req,
            HttpServletResponse resp) throws IOException {
        String url = req.getRequestURI();
        Map<String, String> urlVariables = Route.getURLVariables(url, "/hello/<name>");
        // get <name> from the url
        String name = urlVariables.get("name");
        writeResponse(resp, "Hello " + name, HttpServletResponse.SC_OK);
    }
}
