
package com.diego.tienda.service;

import com.diego.tienda.model.Producto;
import com.diego.tienda.repository.ProductoRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Obtener todos los productos
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Buscar producto por ID
    public Optional<Producto> obtenerProducto(Long id) {
        return productoRepository.findById(id);
    }

    // Registrar producto
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Actualizar producto
    public Optional<Producto> actualizarProducto(Long id, Producto datos) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(datos.getNombre());
                    producto.setDescripcion(datos.getDescripcion());
                    producto.setPrecio(datos.getPrecio());
                    producto.setStock(datos.getStock());

                    return productoRepository.save(producto);
                });
    }

    // Eliminar producto
    public boolean eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            return false;
        }

        productoRepository.deleteById(id);
        return true;
    }
}
