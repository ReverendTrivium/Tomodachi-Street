package org.tomodachi.controllers.error;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
public class ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        model.addAttribute("status", status);
        model.addAttribute("error", errorMessage);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 403) {
                return "error/403"; // Return the custom 403 error page
            } else if (statusCode == 404) {
                return "error/404"; // Custom 404 error page
            } else if (statusCode == 500) {
                return "error/500"; // Custom 500 error page
            }
        }

        return "error/general"; // Fallback to a general error page
    }

    public String getErrorPath() {
        return "/error";
    }
}
