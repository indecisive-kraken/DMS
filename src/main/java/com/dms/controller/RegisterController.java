package com.dms.controller;

import com.dms.core.exceptions.EntityAlreadyExistsException;
import com.dms.dto.UserInsertDTO;
import com.dms.repository.RoleRepository;
import com.dms.service.UserService;
import com.dms.validator.UserInsertValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dms")
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;
    private final UserInsertValidator userInsertValidator;
    private final RoleRepository roleRepository;

    @GetMapping("/users/register")
    public String getUserForm(Model model) {

        model.addAttribute("userInsertDTO", new UserInsertDTO());
        model.addAttribute("roles", roleRepository.findAll(Sort.by("name")));
        return "registration-form";
    }

    @PostMapping("/users/register")
    public String insertUser(@Valid @ModelAttribute("userInsertDTO") UserInsertDTO userInsertDTO,
                             BindingResult bindingResult, Model model) {

        userInsertValidator.validate(userInsertDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll(Sort.by("name")));
            return "registration-form";
        }

        try {

            userService.saveUser(userInsertDTO);
            return "redirect:/";
        } catch (EntityAlreadyExistsException e) {
            model.addAttribute("roles", roleRepository.findAll(Sort.by("name")));
            model.addAttribute("errorMessage", e.getMessage());
            return "registration-form";
        }
    }
}
