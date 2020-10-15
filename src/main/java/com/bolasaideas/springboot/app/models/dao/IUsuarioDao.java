package com.bolasaideas.springboot.app.models.dao;

import com.bolasaideas.springboot.app.models.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Dipper
 * @project spring-boot-data-jpa
 * @created 07/10/2020 - 16:34
 */
public interface IUsuarioDao extends CrudRepository<Usuario, Long> {
    public Usuario findByUsername(String username);
}
