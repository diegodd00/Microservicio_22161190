package com.diego.tienda.controller;

import com.diego.tienda.model.Venta;
import com.diego.tienda.service.VentaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    // Listar todas las ventas
    @GetMapping
    public List<Venta> listarVentas() {
        return ventaService.listarVentas();
    }

    // Buscar venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVenta(@PathVariable Long id) {
        return ventaService.obtenerVenta(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Registrar venta
    @PostMapping
    public ResponseEntity<Venta> guardarVenta(@RequestBody Venta venta) {

        Venta nuevaVenta = ventaService.guardarVenta(venta);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
    }

    // Actualizar venta
    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizarVenta(
            @PathVariable Long id,
            @RequestBody Venta venta) {

        return ventaService.actualizarVenta(id, venta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar venta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {

        if (!ventaService.eliminarVenta(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}