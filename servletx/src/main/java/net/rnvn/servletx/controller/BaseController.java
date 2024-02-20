package net.rnvn.servletx.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// esta clase esta pensada para que todo controlador que desee realizar solicitudes rest
// mediante JSON herede de ella (sin autenticacion)
public class BaseController extends HttpServlet {

    // abstract methods [onGet, onPost]

    protected void onGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        writeResponse(resp, HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    protected void onPost(
            HttpServletRequest req,
            HttpServletResponse resp,
            String body)
            throws ServletException, IOException {
        writeResponse(resp, HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    protected void onPut(
            HttpServletRequest req,
            HttpServletResponse resp,
            String body)
            throws ServletException, IOException {
        writeResponse(resp, HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    protected void onDelete(
            HttpServletRequest req,
            HttpServletResponse resp,
            String body)
            throws ServletException, IOException {
        writeResponse(resp, HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        parseRequest(req, resp, (request, response, body) -> {
            try {
                onGet(request, response);
            } catch (Exception e) {
                System.err.println(this.getClass().getName() + ": " + e.getMessage());
                writeResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        });
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        parseRequest(req, resp, (request, response, body) -> {
            try {
                onPost(request, response, body);
            } catch (Exception e) {
                System.err.println(this.getClass().getName() + ": " + e.getMessage());
                writeResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        });
    }

    @Override
    protected void doPut(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        parseRequest(req, resp, (request, response, body) -> {
            try {
                onPut(request, response, body);
            } catch (Exception e) {
                System.err.println(this.getClass().getName() + ": " + e.getMessage());
                writeResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        });
    }

    @Override
    protected void doDelete(
            HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {
        parseRequest(req, resp, (request, response, body) -> {
            try {
                onDelete(request, response, body);
            } catch (Exception e) {
                System.err.println(this.getClass().getName() + ": " + e.getMessage());
                writeResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        });
    }

    private void parseRequest(
            HttpServletRequest req,
            HttpServletResponse resp,
            // callback(request, response, jsonObject)
            HTTPCallback callback)
            throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        String body = null;
        // verificamos si el request contiene un body
        if (req.getContentLength() > 0) {
            try (
                    java.io.BufferedReader reader = req.getReader();) {
                body = reader.lines().reduce("", String::concat);
            } catch (Exception e) {
                System.err.println(this.getClass().getName() + ": " + e.getMessage());
            }
        }
        try {
            callback.handleRequest(req, resp, body);
        } catch (Exception e) {
            System.err.println(this.getClass().getName() + ": " + e.getMessage());
            writeResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // este metodo simplifica la implementacion de la interfaz funcional
    protected void writeResponse(
            HttpServletResponse resp,
            Object jsonObject)
            throws IOException {
        writeResponse(resp, jsonObject, HttpServletResponse.SC_OK);
    }

    protected void writeResponse(
            HttpServletResponse resp,
            int HTTPStatus) {
        try {
            writeResponse(resp, null, HTTPStatus);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeResponse(
            HttpServletResponse resp,
            Object response,
            int HTTPStatus)
            throws IOException {
        resp.setStatus(HTTPStatus);
        if (null != response) {
            resp.getWriter().write(response.toString());
        }
    }

    @FunctionalInterface
    public interface HTTPCallback {
        public void handleRequest(HttpServletRequest t, HttpServletResponse u, String body);
    }
}