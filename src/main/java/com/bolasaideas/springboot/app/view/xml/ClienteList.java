package com.bolasaideas.springboot.app.view.xml;

import com.bolasaideas.springboot.app.models.entities.Cliente;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Dipper
 * @project spring-boot-data-jpa
 * @created 13/10/2020 - 11:47
 */
@XmlRootElement(name = "clientes")
public class ClienteList {

    @XmlElement(name = "cliente")
    public List<Cliente> clientes;

    public ClienteList() {
    }

    public ClienteList(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
