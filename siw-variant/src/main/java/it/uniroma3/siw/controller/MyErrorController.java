package it.uniroma3.siw.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class MyErrorController implements ErrorController {

    @GetMapping("/error")
    public String useAValidLinkNextTimeLad(HttpServletRequest request) {
        return "redirect:https://youtu.be/51zjlMhdSTE?t=8";
    }

}