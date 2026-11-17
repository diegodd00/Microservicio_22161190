
<script setup>
import { ref, onMounted } from 'vue'
import {
  obtenerProductos,
  crearProducto,
  actualizarProducto,
  eliminarProducto
} from '../services/productoService'

const productos = ref([])
const cargando = ref(false)
const guardando = ref(false)
const error = ref('')
const mensaje = ref('')

const mostrarFormulario = ref(false)
const modoEdicion = ref(false)
const productoId = ref(null)

const formulario = ref({
  nombre: '',
  descripcion: '',
  precio: '',
  stock: ''
})

const cargarProductos = async () => {
  cargando.value = true
  error.value = ''

  try {
    const respuesta = await obtenerProductos()
    productos.value = respuesta.data
  } catch (err) {
    console.error(err)
    error.value = 'No se pudieron cargar los productos.'
  } finally {
    cargando.value = false
  }
}

const limpiarFormulario = () => {
  formulario.value = {
    nombre: '',
    descripcion: '',
    precio: '',
    stock: ''
  }

  productoId.value = null
  modoEdicion.value = false
}

const abrirFormulario = () => {
  limpiarFormulario()
  mostrarFormulario.value = true
  mensaje.value = ''
}

const editarProducto = (producto) => {
  formulario.value = {
    nombre: producto.nombre,
    descripcion: producto.descripcion,
    precio: producto.precio,
    stock: producto.stock
  }

  productoId.value = producto.id
  modoEdicion.value = true
  mostrarFormulario.value = true
  mensaje.value = ''
}

const guardarProducto = async () => {
  guardando.value = true
  error.value = ''
  mensaje.value = ''

  const datos = {
    nombre: formulario.value.nombre.trim(),
    descripcion: formulario.value.descripcion.trim(),
    precio: Number(formulario.value.precio),
    stock: Number(formulario.value.stock)
  }

  try {
    if (modoEdicion.value) {
      await actualizarProducto(productoId.value, datos)
      mensaje.value = 'Producto actualizado correctamente.'
    } else {
      await crearProducto(datos)
      mensaje.value = 'Producto registrado correctamente.'
    }

    mostrarFormulario.value = false
    limpiarFormulario()
    await cargarProductos()
  } catch (err) {
    console.error(err)
    error.value = 'No se pudo guardar el producto. Verifica los datos.'
  } finally {
    guardando.value = false
  }
}

const confirmarEliminar = async (id) => {
  if (!confirm('¿Estás seguro de eliminar este producto?')) return

  error.value = ''
  mensaje.value = ''

  try {
    await eliminarProducto(id)
    mensaje.value = 'Producto eliminado correctamente.'
    await cargarProductos()
  } catch (err) {
    console.error(err)
    error.value = 'No se pudo eliminar el producto.'
  }
}

onMounted(cargarProductos)
</script>

<template>
  <div>
    <div class="d-flex justify-content-between align-items-center mb-4">
      <div>
        <h2 class="fw-bold">Productos</h2>
        <p class="text-secondary mb-0">
          Administra el inventario de tu tienda.
        </p>
      </div>

      <button class="btn btn-primary" @click="abrirFormulario">
        <i class="bi bi-plus-lg me-2"></i>
        Nuevo producto
      </button>
    </div>

    <div v-if="mensaje" class="alert alert-success">
      {{ mensaje }}
    </div>

    <div v-if="error" class="alert alert-danger">
      {{ error }}
    </div>

    <!-- Formulario -->
    <div v-if="mostrarFormulario" class="card border-0 shadow-sm mb-4">
      <div class="card-header bg-white py-3">
        <h5 class="mb-0 fw-bold">
          {{ modoEdicion ? 'Editar producto' : 'Registrar producto' }}
        </h5>
      </div>

      <div class="card-body">
        <form @submit.prevent="guardarProducto">
          <div class="row g-3">
            <div class="col-md-6">
              <label class="form-label">Nombre</label>
              <input
                v-model="formulario.nombre"
                type="text"
                class="form-control"
                required
                maxlength="100"
              />
            </div>

            <div class="col-md-6">
              <label class="form-label">Descripción</label>
              <input
                v-model="formulario.descripcion"
                type="text"
                class="form-control"
                maxlength="255"
              />
            </div>

            <div class="col-md-6">
              <label class="form-label">Precio</label>
              <input
                v-model="formulario.precio"
                type="number"
                class="form-control"
                min="0.01"
                step="0.01"
                required
              />
            </div>

            <div class="col-md-6">
              <label class="form-label">Stock</label>
              <input
                v-model="formulario.stock"
                type="number"
                class="form-control"
                min="0"
                step="1"
                required
              />
            </div>
          </div>

          <div class="mt-4 d-flex gap-2">
            <button
              type="submit"
              class="btn btn-primary"
              :disabled="guardando"
            >
              {{ guardando ? 'Guardando...' : 'Guardar producto' }}
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

    <!-- Tabla -->
    <div class="card border-0 shadow-sm">
      <div class="card-body">
        <div v-if="cargando" class="text-center py-4">
          Cargando productos...
        </div>

        <div v-else-if="productos.length === 0" class="text-center py-5">
          <i class="bi bi-box-seam fs-1 text-secondary"></i>
          <h5 class="mt-3">No hay productos registrados</h5>
          <p class="text-secondary">
            Agrega tu primer producto para comenzar.
          </p>
        </div>

        <div v-else class="table-responsive">
          <table class="table align-middle">
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Precio</th>
                <th>Stock</th>
                <th class="text-end">Acciones</th>
              </tr>
            </thead>

            <tbody>
              <tr v-for="producto in productos" :key="producto.id">
                <td>{{ producto.id }}</td>
                <td class="fw-semibold">{{ producto.nombre }}</td>
                <td>{{ producto.descripcion || 'Sin descripción' }}</td>
                <td>${{ Number(producto.precio).toFixed(2) }}</td>
                <td>{{ producto.stock }}</td>

                <td class="text-end">
                  <button
                    class="btn btn-sm btn-outline-primary me-2"
                    @click="editarProducto(producto)"
                    title="Editar"
                  >
                    <i class="bi bi-pencil"></i>
                  </button>

                  <button
                    class="btn btn-sm btn-outline-danger"
                    @click="confirmarEliminar(producto.id)"
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
