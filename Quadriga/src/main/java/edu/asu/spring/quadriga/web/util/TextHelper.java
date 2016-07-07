package edu.asu.spring.quadriga.web.util;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;

@Service
public class TextHelper {

    public ResponseEntity<String> getResponse(String content, HttpServletResponse response)
            throws QuadrigaAccessException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        String respMessage = null;
        respMessage = StringEscapeUtils.escapeHtml(content);
        respMessage = respMessage.replace("\n", "<br>");
        return new ResponseEntity<String>(respMessage, HttpStatus.OK);
    }
}
