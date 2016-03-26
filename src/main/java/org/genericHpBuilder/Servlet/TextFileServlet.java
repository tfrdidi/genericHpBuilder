package org.genericHpBuilder.Servlet;

import org.genericHpBuilder.Controller.TextFileManager;
import org.genericHpBuilder.Model.TextFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Abstract super class for text file serving servlets.
 */
public class TextFileServlet extends HttpServlet{

        private final Logger log = LoggerFactory.getLogger(TextFileServlet.class);

        final int CACHE_DURATION_IN_SECONDS = 60 * 60; // 1 hour
        final long CACHE_DURATION_IN_MS = CACHE_DURATION_IN_SECONDS  * 1000;

        private TextFileManager textFileManager = new TextFileManager();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
            try {
                log.info("Request: " + req.getRequestURI());
                myGet(req, resp);
                resp.addHeader("Cache-Control", "max-age=" + CACHE_DURATION_IN_SECONDS);
                resp.setDateHeader("Expires", System.currentTimeMillis() + CACHE_DURATION_IN_MS);
            }
            catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                log.warn("Object " + req.getRequestURI() + " not found.");
            }
        }

        protected void myGet(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp)
                throws ServletException, IOException {

            String textFileName = req.getRequestURI();
            TextFile textFile = textFileManager.getTextFile(textFileName);

            if(textFile == null) {
                throw new IllegalArgumentException("No undeleted text file " + textFileName + " was found");
            }

            String content = textFile.getCurrentTextFileversion().getContent();
            streamStringAsResponse(content, resp);

        }

        protected void streamStringAsResponse(String content, HttpServletResponse resp) throws IOException {
            PrintWriter out = resp.getWriter();
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html;charset=UTF-8");
            out.write(content);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
}
