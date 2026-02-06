package com.dms.controller;

import com.dms.core.exceptions.EntityAlreadyExistsException;
import com.dms.core.exceptions.EntityInvalidArgumentException;
import com.dms.core.exceptions.EntityNotFoundException;
import com.dms.dto.ClientEditDTO;
import com.dms.dto.ClientInsertDTO;
import com.dms.dto.ClientReadOnlyDTO;
import com.dms.dto.TaskEditDTO;
import com.dms.mapper.Mapper;
import com.dms.model.Client;
import com.dms.repository.ClientRepository;
import com.dms.service.IClientService;
import com.dms.validator.ClientEditValidator;
import com.dms.validator.ClientInsertValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clients/dashboard")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientRepository clientRepository;
    private final IClientService clientService;
    private final Mapper mapper;
    private final ClientInsertValidator clientInsertValidator;
    private final ClientEditValidator clientEditValidator;


    @GetMapping("/insert")
    public String getClientForm(Model model) {

        model.addAttribute("clientInsertDTO", new ClientInsertDTO());
        return "client-form";
    }

    @PostMapping("/insert")
    public String saveClient(@Valid @ModelAttribute("clientInsertDTO") ClientInsertDTO clientInsertDTO,
                             BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws EntityAlreadyExistsException, EntityNotFoundException {
        Client savedClient;

        clientInsertValidator.validate(clientInsertDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "client-form";
        }

        try {
            savedClient = clientService.saveClient(clientInsertDTO);
            return "redirect:/clients/dashboard";

        } catch (EntityAlreadyExistsException | EntityNotFoundException e) {

            model.addAttribute("errorMessage", e.getMessage());
            return "client-form";
        }
    }

    @GetMapping
    public String getPaginatedClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

//            @RequestParam(defaultValue = "client") String sortBy
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
//        clientRepository.findClients(pageable);
        Page<ClientReadOnlyDTO> clientsPage = clientService.getPaginatedClients(page, size);

        model.addAttribute("clientsPage", clientsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", clientsPage.getTotalPages());
        model.addAttribute("sortedClients", clientsPage);
        return "client-dashboard";
    }

    @GetMapping("/edit/{cid}")
    public String showEditForm(@PathVariable String cid, Model model) {

        try {
            Client client = clientRepository.findByCid(cid)
                    .orElseThrow(() -> new EntityNotFoundException("Client", "client not found"));

            model.addAttribute("clients", clientRepository.findAll(Sort.by("companyName")));
            model.addAttribute("clientEditDTO", mapper.mapToClientEditDTO(client));

            return "client-edits-form";

        } catch (EntityNotFoundException e) {
            log.error("Client with cid={} was not found", cid, e);
            model.addAttribute("errorMessage", e.getMessage());
            return "client-edits-form";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/edit")
    public String updateClient(@Valid @ModelAttribute("clientEditDTO") ClientEditDTO clientEditDTO,
                             BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        clientEditValidator.validate(clientEditDTO, bindingResult);
        model.addAttribute("clients", clientRepository.findAll());
        if (bindingResult.hasErrors()) {
            return "client-edits-form";
        }

        try {
            clientService.updateClient(clientEditDTO);
            return "redirect:/clients/dashboard";

        } catch (EntityAlreadyExistsException | EntityInvalidArgumentException | EntityNotFoundException e) {

            model.addAttribute("errorMessage", e.getMessage());
            return "client-edits-form";
        }
    }

    @GetMapping("/delete/{cid}")
    public String deleteClient(@PathVariable String cid, Model model) {

        try {
            clientService.deleteClientByCid(cid);
            return "redirect:/clients/dashboard";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "client-dashboard";
        }
    }


}