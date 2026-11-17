
package com.diego.tienda.repository;

import com.diego.tienda.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository
        extends JpaRepository<Venta, Long> {

}
