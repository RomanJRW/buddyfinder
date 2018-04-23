package com.joshwindels.buddyfinder.controllers;

import com.joshwindels.buddyfinder.dos.CurrentUser;
import com.joshwindels.buddyfinder.dtos.ExcursionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/excursions")
public class ExcursionController {

    @Autowired
    CurrentUser currentUser;

    @PostMapping("/create")
    public @ResponseBody String createExcursion(ExcursionDTO excursionDTO) {


        return "excursion created";
    }


}
