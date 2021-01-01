<template>

  <div class="main-container bg-pan-bottom">
    <q-btn class="logo-container no-shadow" unelevated to="/">
      <img alt="Quasar logo" src="~assets/quasar-logo-full.svg">
    </q-btn>

    <div class="main-page-container">

      <div class="main-message text-center">
        <h5>Welcome to our Todo App</h5>
      </div>

      <div class="button-container">
        <div class="q-pa-md q-gutter-md">
          <q-btn label="Login"
                 to="/login"
                 icon="account_circle"
                 size="md"
          />
          <q-btn label="Create account"
                 to="/signup"
                 icon="person_add"
                 size="md"
          />
        </div>

      </div>

    </div>

  </div>
</template>

<script>
export default {
  name: 'PageIndex'
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
