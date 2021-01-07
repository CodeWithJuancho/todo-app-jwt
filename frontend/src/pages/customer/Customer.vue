<template>

  <div class="main-container bg-pan-bottom">

    <div class="main-page-container">
      <div class="main-message text-center">
        <h2>{{user}}</h2>
        <h5>Welcome to our Todo App Customer Page</h5>
      </div>
    </div>

  </div>
</template>

<script>
import Auth from '../../service/Auth'

export default {
  name: 'Customer',

  data() {
    return {
      user: null
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

</style>
