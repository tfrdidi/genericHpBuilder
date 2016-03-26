package org.genericHpBuilder.Servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Abstract super class for all edit servlets of the different text file types.
 */
public abstract class EditTextFileServlet extends HttpServlet {

    private final Logger log = LoggerFactory.getLogger(EditTextFileServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doPost");
        String textFileName = req.getParameter("textFileName");
        log.info("textFileName: " + textFileName);
        String content = req.getParameter("content");
        log.info("content: " + content);
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doPut");
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doDelete");
        super.doDelete(req, resp);
    }
}
