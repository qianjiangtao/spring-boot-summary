package com.lvmama.tony.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *     freemarker模版引擎
 * <p>
 *
 * @author Tony-J
 * @date 2018/12/24
 */
@Controller
@RequestMapping(value = "/freemarker")
public class MyFreemarkerController {
    @GetMapping(value = "/index")
    public String freemarkerIndex(ModelMap modelMap) {
        modelMap.addAttribute("username", "Tony-J");
        return "freemarker";
    }
}
