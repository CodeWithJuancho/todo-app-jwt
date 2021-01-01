<template>

  <div class="main-container bg-pan-bottom">
    <q-btn class="logo-container no-shadow" unelevated to="/">
      <img alt="Quasar logo" src="~assets/quasar-logo-full.svg">
    </q-btn>

    <div class="login">
      <q-form @submit="login" class="q-gutter-md">

        <q-input v-model="userName"
                 label="Username"
                 :rules="[val => !!val || 'Username must be full filled']"
                 bg-color="lightgrey"
                 filled
        />

        <q-input v-model="password"
                 :type="isPasswordHidden ? 'password':'text'"
                 label="Password"
                 :rules="[val => !!val || 'Password must be full filled']"
                 bg-color="lightgrey"
                 filled
        >
          <template v-slot:append>
            <q-icon @click="isPasswordHidden = !isPasswordHidden"
                    :name="isPasswordHidden ? 'visibility_off':'visibility'"
            />
          </template>
        </q-input>

        <q-btn label="Login"
               type="submit"
               class="login-btn"
               color="primary"
        />
      </q-form>
    </div>


  </div>
</template>

<script>
export default {

  name: 'Login',

  data() {
    return {
      userName: null,
      password: null,
      isPasswordHidden: true
    }
  },

  methods: {
    login() {
      console.log('Hello this will be the login')
    }
  }
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

.login {
  max-width: 400px;
  text-align: center;
  color: white;
  padding: 30px;
  margin: 30px auto;
}

.login-btn {
  width: 90%;
  height: 45px;
}
</style>
