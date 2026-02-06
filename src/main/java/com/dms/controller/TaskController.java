package com.dms.controller;

import com.dms.core.exceptions.EntityAlreadyExistsException;
import com.dms.core.exceptions.EntityInvalidArgumentException;
import com.dms.core.exceptions.EntityNotFoundException;
import com.dms.dto.ClientReadOnlyDTO;
import com.dms.dto.TaskEditDTO;
import com.dms.dto.TaskReadOnlyDTO;
import com.dms.dto.TaskInsertDTO;
import com.dms.mapper.Mapper;
import com.dms.model.Client;
import com.dms.model.Task;
import com.dms.repository.ClientRepository;
import com.dms.repository.TaskRepository;
import com.dms.service.IClientService;
import com.dms.service.ITaskService;
import com.dms.validator.TaskEditValidator;
import com.dms.validator.TaskInsertValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/tasks/dashboard")
@RequiredArgsConstructor
@Slf4j
public class TaskController  {


    private final ITaskService taskService;
    private final IClientService clientService;
    private final TaskRepository taskRepository;
    private final ClientRepository clientRepository;
    private final Mapper mapper;
    private final TaskInsertValidator taskInsertValidator;
    private final TaskEditValidator taskEditValidator;

    @GetMapping("/insert")
    public String gettaskForm(Model model) {

        model.addAttribute("taskInsertDTO", new TaskInsertDTO());     // model request scope
        model.addAttribute("clients", clientRepository.findAll());
        return "task-form";
    }

    @PostMapping("/insert")
    public String saveTask(@Valid @ModelAttribute("taskInsertDTO") TaskInsertDTO taskInsertDTO,
                           BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws Exception {

        Task savedtask;
        taskInsertValidator.validate(taskInsertDTO, bindingResult);
        if (bindingResult.hasErrors()) {

            // Caveman debugging to the rescue
            System.out.println(bindingResult.getAllErrors());
            return "task-form";
        }

        // If we wanted to respond with a success page with task details,
        // then we would need the following in comments. Otherwise, if no success page
        // we can just redirect to tasks view
        //            taskReadOnlyDTO readOnlyDTO = mapper.mapTotaskReadOnlyDTO(savedtask);
        //            redirectAttributes.addFlashAttribute("task", readOnlyDTO);


        try {

            model.addAttribute("client", clientRepository.findAll());
            savedtask = taskService.saveTask(taskInsertDTO);
            return "redirect:/tasks/dashboard";

        } catch (EntityAlreadyExistsException | EntityInvalidArgumentException e) {

            model.addAttribute("errorMessage", e.getMessage());
            return "task-form";
        }
    }

    @GetMapping
    public String getPaginatedtasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        Page<TaskReadOnlyDTO> tasksPage = taskService.getPaginatedTasks(page, size);

        model.addAttribute("tasksPage", tasksPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", tasksPage.getTotalPages());
        return "task-dashboard";
    }

    @GetMapping("/edit/{uuid}")
    public String showEditForm(@PathVariable String uuid, Model model) {
        try {
            Task task = taskRepository.findByUuid(uuid)
                    .orElseThrow(() -> new EntityNotFoundException("task", "task not found")); // TBD call service

            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("taskEditDTO", mapper.mapToTaskEditDTO(task));

            return "task-edit-form";
        } catch (EntityNotFoundException e) {
            log.error("task with uuid={} was not found.", uuid, e);

            model.addAttribute("errorMessage", e.getMessage());
            return "task-edit-form";
        }
    }

    @PostMapping("/edit")
    public String updatetask(@Valid @ModelAttribute("taskEditDTO") TaskEditDTO taskEditDTO,
                             BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        taskEditValidator.validate(taskEditDTO, bindingResult);
        model.addAttribute("clients", clientRepository.findAll());
        if (bindingResult.hasErrors()) {
            return "task-edit-form";
        }

        // If we wanted to respond with a success page with task details,
        // then we would need the following in comments, and also need from the service to
        // send back the updated task. Otherwise, if no success page
        // we can just redirect to tasks view,

        //taskReadOnlyDTO readOnlyDTO = mapper.mapTotaskReadOnlyDTO(updatedtask);
        //redirectAttributes.addFlashAttribute("task", readOnlyDTO);

        try {
            taskService.updateTask(taskEditDTO);
            return "redirect:/tasks/dashboard";

        } catch (EntityAlreadyExistsException | EntityInvalidArgumentException | EntityNotFoundException e) {

            model.addAttribute("errorMessage", e.getMessage());
            return "task-edit-form";
        }
    }

    @GetMapping("/delete/{uuid}")
    public String deletetask(@PathVariable String uuid, Model model) {
        try {
            taskService.deleteTaskByUUID(uuid);
            return "redirect:/tasks/dashboard";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "task-dashboard";
        }
    }

    public String getTaskByUUID(@PathVariable String uuid, Model model) {

        try{
            taskService.findTaskByUUID(uuid);
            return "redirect:/tasks/dashboard/{uuid}";
        }catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "task-dashboard";
        }
    }


//    //This is stupid fix it or add it to the model you do not return a different page, use this approach to return the list with the AI links and the in-built browser...if you can implement this
    //You don't have to it is stupid, findAll does everything..
//    @GetMapping("/client-list")
//    public String returnClientList(@Valid Model model, RedirectAttributes redirectAttributes) throws Exception{
//
//        ClientReadOnlyDTO crdto = new ClientReadOnlyDTO();
//
//        try {
//
//            List<Client> clientList = clientService.returnClientList();
//            return "client-list";
//        }catch (Exception e){
//            model.addAttribute("Could not return the client list",e.getMessage());
//            return "client-list/task-form-fields.html";
//        }
//    }

}


