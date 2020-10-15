package com.bolasaideas.springboot.app.controllers;

import com.bolasaideas.springboot.app.models.service.IClienteService;
import com.bolasaideas.springboot.app.view.xml.ClienteList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dipper
 * @project spring-boot-data-jpa
 * @created 14/10/2020 - 15:53
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {
    @Autowired
    private IClienteService clienteService;

    @GetMapping(value = "/listar")
    public ClienteList listar() {
        return new ClienteList(clienteService.findAll());
    }
}
