<template>

  <div class="main-container bg-pan-bottom">
    <q-btn class="logo-container no-shadow" unelevated to="/">
      <img alt="Quasar logo" src="~assets/quasar-logo-full.svg">
    </q-btn>

    <div class="sign-up">
      <q-form @submit="createUser" class="q-gutter-md">

        <q-input v-model="userName"
                 label="Username"
                 :rules="[val => !!val || 'Username must be full filled']"
                 bg-color="lightgrey"
                 filled
        />

        <q-input v-model="email"
                 label="Email"
                 :rules="[val => !!val || 'Email must be full filled']"
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
            <q-icon v-if="!password"
                    @click="validPasswordInfo = !validPasswordInfo"
                    name="info"
            />
          </template>
        </q-input>
        <q-card v-if="validPasswordInfo"
                flat
                bordered
                class="my-card text-justify text-weight-bold text-black"
        >
          <q-card-section>
            The password must contain the following
          </q-card-section>

          <q-separator inset="/"/>

          <q-card-section class="q-pa-none">
            <ul>
              <li id="lower-letter">
                At least one lower letter
              </li>
              <li id="upper-letter">
                At least one upper letter
              </li>
              <li id="number">
                At least one number
              </li>
              <li id="no-spaces">
                No spaces
              </li>
              <li id="special-character">
                At least one special character
              </li>
              <li id="length">
                Must be between 8 and 16 characters long
              </li>
            </ul>
          </q-card-section>
        </q-card>

        <q-btn label="Create Account"
               type="submit"
               class="sign-up-btn"
               color="primary"
        />
      </q-form>
    </div>


  </div>
</template>

<script>
export default {

  name: 'SignUp',

  watch: {
    password: function () {
      if (this.validPasswordInfo) {
        const lowerLetterRegEx = /[a-z]/g
        if (this.password.match(lowerLetterRegEx)) {
          this.getClassList('lower-letter').add('valid')
        } else {
          this.getClassList('lower-letter').remove('valid')
        }

        const upperLetterRegEx = /[A-Z]/g
        if (this.password.match(upperLetterRegEx)) {
          this.getClassList('upper-letter').add('valid')
        } else {
          this.getClassList('upper-letter').remove('valid')
        }

        const numberRegEx = /\d/g
        if (this.password.match(numberRegEx)) {
          this.getClassList('number').add('valid')
        } else {
          this.getClassList('number').remove('valid')
        }

        const specialCharacterRegEx = /[^a-zA-Z0-9 ]/g
        if (this.password.match(specialCharacterRegEx)) {
          this.getClassList('special-character').add('valid')
        } else {
          this.getClassList('special-character').remove('valid')
        }

        const space = / /g
        if (!this.password.match(space)) {
          this.getClassList('no-spaces').add('valid')
        } else {
          this.getClassList('no-spaces').remove('valid')
        }

        if (this.password.length >= this.passwordMinLength && this.password.length <= this.passwordMaxLength) {
          this.getClassList('length').add('valid')
        } else {
          this.getClassList('length').remove('valid')
        }

      }
    }
  },

  data() {
    return {
      userName: null,
      email: null,
      password: null,
      isPasswordHidden: true,
      validPasswordInfo: false,
      passwordMinLength: 8,
      passwordMaxLength: 16
    }
  },

  methods: {

    createUser() {
      console.log('Hello this will be where the user creates its account')
    },

    getClassList(elementId) {
      return document.getElementById(elementId).classList
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

.sign-up {
  max-width: 400px;
  text-align: center;
  color: white;
  padding: 30px;
  margin: 30px auto;
}

.sign-up-btn {
  width: 90%;
  height: 45px;
}

ul {
  list-style: circle;
  color: red;
}

.valid {
  list-style: square;
  color: green;
}
</style>
