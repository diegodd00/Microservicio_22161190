
<script setup>
import Sidebar from './components/Sidebar.vue'
import Header from './components/Header.vue'
</script>

<template>
  <div class="app-layout">
    <Sidebar />

    <div class="main-content">
      <Header />

      <main class="page-content">
        <RouterView />
      </main>
    </div>
  </div>
</template>

<style>
.app-layout {
  display: flex;
  min-height: 100vh;
}

.main-content {
  flex: 1;
  min-width: 0;
}

.page-content {
  padding: 30px;
}
</style>
