
import { createRouter, createWebHistory } from 'vue-router'

import Dashboard from '../views/Dashboard.vue'
import Productos from '../views/Productos.vue'
import Ventas from '../views/Ventas.vue'

const routes = [
  {
    path: '/',
    component: Dashboard
  },
  {
    path: '/productos',
    component: Productos
  },
  {
    path: '/ventas',
    component: Ventas
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
