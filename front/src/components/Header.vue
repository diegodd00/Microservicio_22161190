
<template>
  <header class="header">
    <div>
      <h5>Panel administrativo</h5>
      <small>Bienvenido a tu sistema de tienda</small>
    </div>

    <div class="header-user">
      <div class="user-avatar">D</div>
      <div>
        <strong>Administrador</strong>
        <small>Gestión de tienda</small>
      </div>
    </div>
  </header>
</template>

<style scoped>
.header {
  height: 85px;
  background: white;
  padding: 0 35px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #e9edf3;
}

.header h5 {
  margin: 0;
  font-weight: 700;
}

.header small {
  color: #8994a5;
  font-size: 12px;
}

.header-user {
  display: flex;
  align-items: center;
  gap: 12px;
}

.header-user strong,
.header-user small {
  display: block;
}

.user-avatar {
  width: 42px;
  height: 42px;
  border-radius: 50%;
  display: grid;
  place-items: center;
  background: #e8edff;
  color: #3867f5;
  font-weight: bold;
}
</style>
