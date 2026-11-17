
package com.diego.tienda.service;

import com.diego.tienda.model.Venta;
import com.diego.tienda.repository.VentaRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    // Obtener todas las ventas
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    // Buscar venta por ID
    public Optional<Venta> obtenerVenta(Long id) {
        return ventaRepository.findById(id);
    }

    // Registrar venta
    public Venta guardarVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    // Actualizar venta
    public Optional<Venta> actualizarVenta(Long id, Venta datos) {
    return ventaRepository.findById(id)
            .map(venta -> {

                if (datos.getFecha() != null) {
                    venta.setFecha(datos.getFecha());
                }

                if (datos.getTotal() != null) {
                    venta.setTotal(datos.getTotal());
                }

                return ventaRepository.save(venta);
            });
}

    // Eliminar venta
    public boolean eliminarVenta(Long id) {
        if (!ventaRepository.existsById(id)) {
            return false;
        }

        ventaRepository.deleteById(id);
        return true;
    }

}
