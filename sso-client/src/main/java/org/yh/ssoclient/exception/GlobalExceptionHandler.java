package org.yh.ssoclient.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: yh
 * @Date: 2020/9/19
 * @Description:
 */

//@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler({Exception.class})
    public void handler(HttpServletResponse response, Exception e) throws IOException {
        log.error(e.getMessage());
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(e.getMessage());
        out.flush();
        out.close();
    }
}
