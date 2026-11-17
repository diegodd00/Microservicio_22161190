
<script setup>
import { ref, onMounted } from 'vue'
import { obtenerProductos } from '../services/productoService'
import { obtenerVentas } from '../services/ventaService'

const productos = ref([])
const ventas = ref([])

const cargando = ref(true)
const error = ref('')

const totalProductos = ref(0)
const totalVentas = ref(0)
const totalIngresos = ref(0)
const valorInventario = ref(0)

const cargarDatos = async () => {
  cargando.value = true
  error.value = ''

  try {
    const [respuestaProductos, respuestaVentas] = await Promise.all([
      obtenerProductos(),
      obtenerVentas()
    ])

    productos.value = respuestaProductos.data
    ventas.value = respuestaVentas.data

    totalProductos.value = productos.value.length
    totalVentas.value = ventas.value.length

    totalIngresos.value = ventas.value.reduce(
      (total, venta) => total + Number(venta.total || 0),
      0
    )

    valorInventario.value = productos.value.reduce(
      (total, producto) =>
        total + Number(producto.precio || 0) * Number(producto.stock || 0),
      0
    )
  } catch (err) {
    console.error(err)
    error.value = 'No se pudo obtener la información de la tienda.'
  } finally {
    cargando.value = false
  }
}

const moneda = (cantidad) => {
  return Number(cantidad).toLocaleString('es-MX', {
    style: 'currency',
    currency: 'MXN'
  })
}

onMounted(cargarDatos)
</script>

<template>
  <div>
    <div class="d-flex justify-content-between align-items-center mb-4">
      <div>
        <h2 class="fw-bold">Dashboard</h2>
        <p class="text-secondary mb-0">
          Resumen general de tu tienda.
        </p>
      </div>

      <button
        class="btn btn-outline-primary"
        @click="cargarDatos"
        :disabled="cargando"
      >
        <i class="bi bi-arrow-clockwise me-2"></i>
        Actualizar
      </button>
    </div>

    <div v-if="cargando" class="alert alert-info">
      Cargando información...
    </div>

    <div v-else-if="error" class="alert alert-danger">
      {{ error }}
      <button
        class="btn btn-sm btn-outline-danger ms-3"
        @click="cargarDatos"
      >
        Reintentar
      </button>
    </div>

    <div v-else>
      <div class="row g-4">

        <div class="col-sm-6 col-xl-3">
          <div class="card border-0 shadow-sm p-4 h-100">
            <div class="d-flex justify-content-between">
              <div>
                <p class="text-secondary mb-2">Productos</p>
                <h2 class="fw-bold">{{ totalProductos }}</h2>
              </div>
              <i class="bi bi-box-seam fs-1 text-primary"></i>
            </div>
            <small class="text-secondary">Productos registrados</small>
          </div>
        </div>

        <div class="col-sm-6 col-xl-3">
          <div class="card border-0 shadow-sm p-4 h-100">
            <div class="d-flex justify-content-between">
              <div>
                <p class="text-secondary mb-2">Ventas</p>
                <h2 class="fw-bold">{{ totalVentas }}</h2>
              </div>
              <i class="bi bi-receipt fs-1 text-success"></i>
            </div>
            <small class="text-secondary">Ventas realizadas</small>
          </div>
        </div>

        <div class="col-sm-6 col-xl-3">
          <div class="card border-0 shadow-sm p-4 h-100">
            <div class="d-flex justify-content-between">
              <div>
                <p class="text-secondary mb-2">Ingresos</p>
                <h4 class="fw-bold">{{ moneda(totalIngresos) }}</h4>
              </div>
              <i class="bi bi-cash-stack fs-1 text-success"></i>
            </div>
            <small class="text-secondary">Importe acumulado de ventas</small>
          </div>
        </div>

        <div class="col-sm-6 col-xl-3">
          <div class="card border-0 shadow-sm p-4 h-100">
            <div class="d-flex justify-content-between">
              <div>
                <p class="text-secondary mb-2">Inventario</p>
                <h4 class="fw-bold">{{ moneda(valorInventario) }}</h4>
              </div>
              <i class="bi bi-archive fs-1 text-warning"></i>
            </div>
            <small class="text-secondary">Valor del stock disponible</small>
          </div>
        </div>

      </div>

      <div class="card border-0 shadow-sm mt-4">
        <div class="card-body">
          <h5 class="fw-bold mb-3">Actividad reciente</h5>

          <div v-if="ventas.length === 0" class="text-secondary">
            Todavía no hay ventas registradas.
          </div>

          <div v-else class="table-responsive">
            <table class="table align-middle mb-0">
              <thead>
                <tr>
                  <th>Venta</th>
                  <th>Fecha</th>
                  <th class="text-end">Total</th>
                </tr>
              </thead>

              <tbody>
                <tr
                  v-for="venta in ventas.slice(-5).reverse()"
                  :key="venta.id"
                >
                  <td>#{{ venta.id }}</td>
                  <td>
                    {{ venta.fecha
                      ? new Date(venta.fecha).toLocaleString('es-MX')
                      : 'Sin fecha'
                    }}
                  </td>
                  <td class="text-end fw-semibold">
                    {{ moneda(venta.total) }}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
