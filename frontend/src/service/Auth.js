import ApiAuth from 'src/service/ApiAuth'

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

  // TODO We will place the decode token logic

  // TODO We will place the user logged access levels

}