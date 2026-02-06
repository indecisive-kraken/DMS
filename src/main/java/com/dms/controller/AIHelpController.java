package com.dms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/ai-links")
@RequiredArgsConstructor
@Slf4j
public class AIHelpController {

    @GetMapping("/ai-list")
    public String populateList(@Valid Model model) {

        List<String> options = new ArrayList<String>();
            options.add("https://chat.openai.com/");
            options.add("https://gemini.google.com/");
            options.add("https://claude.ai/");
            options.add("https://perplexity.ai/");
            model.addAttribute("options", options);
        return "ai_recommendation";

    }


}



