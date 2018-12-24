package com.lvmama.tony.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * thymeleaf模版引擎
 * <p>
 *
 * @author Tony-J
 * @date 2018/12/24
 */
@Controller
@RequestMapping("/thymeleaf")
public class MyThymeleafController {

    @GetMapping(value = "/index")
    public String thymeleafIndex(ModelMap modelMap) {
        modelMap.addAttribute("username", "Tony-J");
        return "thymeleaf";
    }
}
