package com.bolasaideas.springboot.app.view.json;

import com.bolasaideas.springboot.app.models.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

/**
 * @author Dipper
 * @project spring-boot-data-jpa
 * @created 14/10/2020 - 10:59
 */

@Component("listar.json")
public class ClienteListJsonView extends MappingJackson2JsonView {

    @Override
    protected Object filterModel(Map<String, Object> model) {
        model.remove("titulo");
        model.remove("page");
        Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
        model.remove("clientes");
        model.put("clientes", clientes.getContent());
        return super.filterModel(model);
    }
}
