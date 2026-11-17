
<script setup>
import { useRoute } from 'vue-router'

const route = useRoute()

const opciones = [
  { nombre: 'Dashboard', ruta: '/', icono: 'bi-grid-1x2' },
  { nombre: 'Productos', ruta: '/productos', icono: 'bi-box-seam' },
  { nombre: 'Ventas', ruta: '/ventas', icono: 'bi-receipt' }
]
</script>

<template>
  <aside class="sidebar">
    <div class="sidebar-brand">
      <div class="brand-icon">
        <i class="bi bi-shop"></i>
      </div>
      <div>
        <h4>Tienda</h4>
        <small>Panel administrativo</small>
      </div>
    </div>

    <div class="sidebar-label">MENÚ PRINCIPAL</div>

    <nav class="sidebar-nav">
      <RouterLink
        v-for="opcion in opciones"
        :key="opcion.ruta"
        :to="opcion.ruta"
        class="sidebar-link"
        :class="{ active: route.path === opcion.ruta }"
      >
        <i :class="['bi', opcion.icono]"></i>
        <span>{{ opcion.nombre }}</span>
      </RouterLink>
    </nav>

    <div class="sidebar-footer">
      <i class="bi bi-shield-check"></i>
      <span>Sistema de tienda</span>
    </div>
  </aside>
</template>

<style scoped>
.sidebar {
  width: 260px;
  min-height: 100vh;
  background: #172235;
  color: white;
  padding: 28px 18px;
  display: flex;
  flex-direction: column;
}

.sidebar-brand {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 45px;
}

.brand-icon {
  background: #3867f5;
  width: 43px;
  height: 43px;
  border-radius: 12px;
  display: grid;
  place-items: center;
  font-size: 22px;
}

.sidebar-brand h4 {
  margin: 0;
  font-size: 19px;
}

.sidebar-brand small {
  color: #a6b1c3;
  font-size: 11px;
}

.sidebar-label {
  color: #8290a6;
  font-size: 11px;
  font-weight: bold;
  margin: 0 0 15px 12px;
}

.sidebar-nav {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.sidebar-link {
  display: flex;
  align-items: center;
  gap: 13px;
  padding: 13px;
  border-radius: 9px;
  color: #b7c2d3;
  transition: 0.2s;
}

.sidebar-link:hover,
.sidebar-link.active {
  background: #2d4370;
  color: white;
}

.sidebar-link i {
  font-size: 19px;
}

.sidebar-footer {
  margin-top: auto;
  color: #8290a6;
  font-size: 12px;
  display: flex;
  gap: 10px;
  padding: 12px;
}
</style>
