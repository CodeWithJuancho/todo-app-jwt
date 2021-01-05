<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>
        <q-toolbar-title class="text-left">
          Todo App
        </q-toolbar-title>
        <q-toolbar-title class="text-right">
          {{user}}
          <q-btn @click="logOut"
                 icon="exit_to_app"
                 round
                 unelevated
          />
        </q-toolbar-title>
      </q-toolbar>
    </q-header>

    <q-page-container>
      <router-view/>
    </q-page-container>
  </q-layout>
</template>

<script>
import Auth from '../../service/Auth'

export default {

  name: 'AdminLayout',

  data() {
    return {
      user: null
    }
  },

  methods: {
    logOut() {
      if (localStorage.token) {
        localStorage.removeItem('token') // FIXME -> HOW TO REALLY UN-VALIDATE THE TOKEN???
        this.$router.push({ name: 'login' })
      }
    }
  },

  mounted() {
    const user = Auth.getUser()
    this.user = user ? user.userName.toUpperCase() : null
  }

}
</script>
