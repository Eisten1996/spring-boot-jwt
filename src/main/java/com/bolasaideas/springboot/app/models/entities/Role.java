package com.bolasaideas.springboot.app.models.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Dipper
 * @project spring-boot-data-jpa
 * @created 07/10/2020 - 16:14
 */
@Entity
@Table(name = "authorities", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "authority"})})
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
