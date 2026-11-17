
<script setup>
import { ref, onMounted } from 'vue'
import {
  obtenerVentas,
  crearVenta,
  actualizarVenta,
  eliminarVenta
} from '../services/ventaService'

const ventas = ref([])
const cargando = ref(false)
const guardando = ref(false)
const error = ref('')
const mensaje = ref('')

const mostrarFormulario = ref(false)
const modoEdicion = ref(false)
const ventaId = ref(null)

const formulario = ref({
  total: ''
})

const cargarVentas = async () => {
  cargando.value = true
  error.value = ''

  try {
    const respuesta = await obtenerVentas()
    ventas.value = respuesta.data
  } catch (err) {
    console.error(err)
    error.value = 'No se pudieron cargar las ventas.'
  } finally {
    cargando.value = false
  }
}

const limpiarFormulario = () => {
  formulario.value = { total: '' }
  ventaId.value = null
  modoEdicion.value = false
}

const abrirFormulario = () => {
  limpiarFormulario()
  mostrarFormulario.value = true
  mensaje.value = ''
}

const editarVenta = (venta) => {
  formulario.value = {
    total: venta.total
  }

  ventaId.value = venta.id
  modoEdicion.value = true
  mostrarFormulario.value = true
  mensaje.value = ''
}

const guardarVenta = async () => {
  guardando.value = true
  error.value = ''
  mensaje.value = ''

  try {
    const datos = {
      total: Number(formulario.value.total)
    }

    if (modoEdicion.value) {
      await actualizarVenta(ventaId.value, datos)
      mensaje.value = 'Venta actualizada correctamente.'
    } else {
      await crearVenta(datos)
      mensaje.value = 'Venta registrada correctamente.'
    }

    mostrarFormulario.value = false
    limpiarFormulario()
    await cargarVentas()
  } catch (err) {
    console.error(err)
    error.value = 'No se pudo guardar la venta.'
  } finally {
    guardando.value = false
  }
}

const confirmarEliminar = async (id) => {
  if (!confirm('¿Estás seguro de eliminar esta venta?')) return

  error.value = ''
  mensaje.value = ''

  try {
    await eliminarVenta(id)
    mensaje.value = 'Venta eliminada correctamente.'
    await cargarVentas()
  } catch (err) {
    console.error(err)
    error.value = 'No se pudo eliminar la venta.'
  }
}

const formatearFecha = (fecha) => {
  if (!fecha) return 'Sin fecha'

  return new Date(fecha).toLocaleString('es-MX')
}

onMounted(cargarVentas)
</script>

<template>
  <div>
    <div class="d-flex justify-content-between align-items-center mb-4">
      <div>
        <h2 class="fw-bold">Ventas</h2>
        <p class="text-secondary mb-0">
          Administra las ventas de tu tienda.
        </p>
      </div>

      <button class="btn btn-primary" @click="abrirFormulario">
        <i class="bi bi-plus-lg me-2"></i>
        Nueva venta
      </button>
    </div>

    <div v-if="mensaje" class="alert alert-success">
      {{ mensaje }}
    </div>

    <div v-if="error" class="alert alert-danger">
      {{ error }}
    </div>

    <div v-if="mostrarFormulario" class="card border-0 shadow-sm mb-4">
      <div class="card-header bg-white py-3">
        <h5 class="mb-0 fw-bold">
          {{ modoEdicion ? 'Editar venta' : 'Registrar venta' }}
        </h5>
      </div>

      <div class="card-body">
        <form @submit.prevent="guardarVenta">
          <div class="mb-3">
            <label class="form-label">Total de la venta</label>

            <div class="input-group">
              <span class="input-group-text">$</span>
              <input
                v-model="formulario.total"
                type="number"
                class="form-control"
                min="0.01"
                step="0.01"
                required
              />
            </div>
          </div>

          <div class="d-flex gap-2">
            <button
              type="submit"
              class="btn btn-primary"
              :disabled="guardando"
            >
              {{ guardando ? 'Guardando...' : 'Guardar venta' }}
            </button>

            <button
              type="button"
              class="btn btn-outline-secondary"
              @click="mostrarFormulario = false"
            >
              Cancelar
            </button>
          </div>
        </form>
      </div>
    </div>

    <div class="card border-0 shadow-sm">
      <div class="card-body">
        <div v-if="cargando" class="text-center py-4">
          Cargando ventas...
        </div>

        <div v-else-if="ventas.length === 0" class="text-center py-5">
          <i class="bi bi-receipt fs-1 text-secondary"></i>
          <h5 class="mt-3">No hay ventas registradas</h5>
          <p class="text-secondary">
            Registra tu primera venta para comenzar.
          </p>
        </div>

        <div v-else class="table-responsive">
          <table class="table align-middle">
            <thead>
              <tr>
                <th>ID</th>
                <th>Fecha</th>
                <th>Total</th>
                <th class="text-end">Acciones</th>
              </tr>
            </thead>

            <tbody>
              <tr v-for="venta in ventas" :key="venta.id">
                <td>{{ venta.id }}</td>
                <td>{{ formatearFecha(venta.fecha) }}</td>
                <td class="fw-semibold">
                  ${{ Number(venta.total).toFixed(2) }}
                </td>

                <td class="text-end">
                  <button
                    class="btn btn-sm btn-outline-primary me-2"
                    @click="editarVenta(venta)"
                    title="Editar"
                  >
                    <i class="bi bi-pencil"></i>
                  </button>

                  <button
                    class="btn btn-sm btn-outline-danger"
                    @click="confirmarEliminar(venta.id)"
                    title="Eliminar"
                  >
                    <i class="bi bi-trash"></i>
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>
