<template>

  <div class="main-container bg-pan-bottom">

    <div class="main-page-container">
      <div class="main-message text-center">
        <h2>{{user}}</h2>
        <h5>Welcome to our Todo App User Page</h5>
      </div>

      <div class="button-container q-pa-md q-gutter-md">
        <q-btn @click="logOut"
               label="Logout"
               icon="exit_to_app"
               size="md"
        />
      </div>
    </div>

  </div>
</template>

<script>
import Auth from '../../service/Auth'

export default {
  name: 'User',

  data() {
    return {
      user: null
    }
  },

  methods: {
    logOut() {
      localStorage.removeItem('token')
      this.$router.push({ name: 'login' })
    }
  },

  mounted() {
    this.user = Auth.getUser() ? Auth.getUser().userName.toUpperCase() : 'No user connected'
  }
}
</script>

<style scoped>
.main-container {
  height: 100vh;
  width: 100vw;
  position: absolute;
}

.button-container {
  text-align: center;
  margin: 20px auto;
}
</style>
