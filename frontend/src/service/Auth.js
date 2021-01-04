import ApiAuth from 'src/service/ApiAuth'
import jwtDecode from 'jwt-decode'

export default {

  login(userName, password, success, failure) {
    if (localStorage.token) {
      localStorage.removeItem('token')
    }
    ApiAuth.login(userName, password,
      (bearerToken) => {
        localStorage.token = bearerToken
        success(!!bearerToken)
      },
      (err) => {
        failure(err)
      })
  },

  getDecodedTokenPayload() {
    if (!localStorage.token) {
      return null
    }
    return jwtDecode(localStorage.token)
  },

  getUser() {
    const decodedToken = this.getDecodedTokenPayload()
    return decodedToken ? {
      name: decodedToken.preferred_username,
      userName: decodedToken.upn,
      roles: decodedToken.groups
    } : null
  },

  isLoggedIn() {
    return !!this.getUser()
  },

  accessLevelAdmin() {
    const tokenUserPayload = this.getUser()
    return tokenUserPayload && tokenUserPayload.roles ? tokenUserPayload.roles.includes('ADMIN') : false
  },

  accessLevelUser() {
    const tokenUserPayload = this.getUser()
    return tokenUserPayload && tokenUserPayload.roles ? tokenUserPayload.roles.includes('USER') : false
  },

  accessLevelCustomer() {
    const tokenUserPayload = this.getUser()
    return tokenUserPayload && tokenUserPayload.roles ? tokenUserPayload.roles.includes('CUSTOMER') : false
  }

}