<template>

  <div class="main-container bg-pan-bottom">
    <q-btn class="logo-container no-shadow" unelevated to="/">
      <img alt="Quasar logo" src="~assets/quasar-logo-full.svg">
    </q-btn>

    <div class="main-page-container">
      <h5>Login</h5>
    </div>

  </div>
</template>

<script>
export default {
  name: 'Login'
}
</script>

<style scoped>
.main-container {
  height: 100vh;
  width: 100vw;
  position: absolute;
}

.logo-container {
  width: 0;
  text-align: center;
  position: center;
  display: flex;
  margin: 30px auto;
}

.button-container {
  text-align: center;
  margin: 20px auto;
}
</style>
