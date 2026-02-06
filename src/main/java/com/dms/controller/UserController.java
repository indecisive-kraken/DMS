package com.dms.controller;

import com.dms.core.exceptions.EntityAlreadyExistsException;
import com.dms.core.exceptions.EntityNotFoundException;
import com.dms.dto.ClientReadOnlyDTO;
import com.dms.dto.UserEditDTO;
import com.dms.dto.UserInsertDTO;
import com.dms.dto.UserReadOnlyDTO;
import com.dms.model.Task;
import com.dms.model.User;
import com.dms.repository.RoleRepository;
import com.dms.repository.UserRepository;
import com.dms.service.UserService;
import com.dms.validator.UserInsertValidator;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/users/dashboard")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserInsertValidator userInsertValidator;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

//    @GetMapping("/insert/{uuid}")
//    public String getUserInsertForm(Model model) {
//
//        model.addAttribute("userInsertDTO", new UserInsertDTO());
//        model.addAttribute("roles", roleRepository.findAll(Sort.by("name")));
//        return "user-form";
//    }

    @GetMapping
    public String getPaginatedUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
//            @RequestParam(defaultValue = "client") String sortBy
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
//        clientRepository.findClients(pageable);
        Page<UserReadOnlyDTO> usersPage = userService.getPaginatedUsers(page, size);

        model.addAttribute("usersPage", usersPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        model.addAttribute("sortedClients", usersPage);
        return "users-dashboard";
    }

//    @PostMapping("/insert")
//    public String insertUser(@Valid @ModelAttribute("userInsertDTO") UserInsertDTO userInsertDTO, BindingResult bindingResult, Model model) {
//
//        userInsertValidator.validate(userInsertDTO, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("roles", roleRepository.findAll(Sort.by("name")));
//            return "user-form";
//        }
//
//        try {
//
//            userService.saveUser(userInsertDTO);
//            return "redirect:/";
//        } catch (EntityAlreadyExistsException e) {
//            model.addAttribute("roles", roleRepository.findAll(Sort.by("name")));
//            model.addAttribute("errorMessage", e.getMessage());
//            return "user-form";
//
//        }
//    }
//
//    @GetMapping("/delete/{uuid}")
//    public String deleteUser(@Valid String uuid, Model model) {
//
//        try {
//            userService.deleteUserByUUID(uuid);
//            return "redirect:/users/dashboard";
//        } catch (EntityNotFoundException e) {
//            model.addAttribute("errorMessage", e.getMessage());
//            return "users-dashboard";
//        }
//
//    }

}
