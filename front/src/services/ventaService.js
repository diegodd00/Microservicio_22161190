
import api from './api'

// Obtener todas las ventas
export const obtenerVentas = () => {
  return api.get('/ventas')
}

// Obtener una venta por ID
export const obtenerVenta = (id) => {
  return api.get(`/ventas/${id}`)
}

// Registrar venta
export const crearVenta = (venta) => {
  return api.post('/ventas', venta)
}

// Actualizar venta
export const actualizarVenta = (id, venta) => {
  return api.put(`/ventas/${id}`, venta)
}

// Eliminar venta
export const eliminarVenta = (id) => {
  return api.delete(`/ventas/${id}`)
}
