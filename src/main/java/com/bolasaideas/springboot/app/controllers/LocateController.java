package com.bolasaideas.springboot.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Dipper
 * @project spring-boot-data-jpa
 * @created 09/10/2020 - 11:36
 */

@Controller
public class LocateController {

    @GetMapping("/locale")
    public String locale(HttpServletRequest request) {
        String ultimaUrl = request.getHeader("referer");
        return "redirect:".concat(ultimaUrl);
    }
}
