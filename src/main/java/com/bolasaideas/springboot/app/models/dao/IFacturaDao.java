package com.bolasaideas.springboot.app.models.dao;

import com.bolasaideas.springboot.app.models.entities.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Eisten Flores
 * @project spring-boot-data-jpa
 */
public interface IFacturaDao extends CrudRepository<Factura, Long> {
    @Query("select f from Factura f join fetch f.cliente c join fetch f.items l join fetch l.producto where f.id = ?1")
    public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);
}
