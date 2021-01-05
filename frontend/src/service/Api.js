import axios from 'axios'

export const AXIOS = axios.create({
  baseURL: process.env.API_URL,
  headers: {
    Accept: 'application/json',
    ContentType: 'application/json'
  }
})

export default {

  callPermitAllEndPoint(success, failure) {
    return AXIOS.get('secured/permit-all')
      .then((response) => {
        success(response.data)
      }, (err) => {
        failure(err.response)
      })
  },

  createAccount(userName, email, password, success, failure) {
    return AXIOS.post('sign-up/user', {
      userName: userName,
      email: email,
      password: password
    })
      .then(() => {
        success(true)
      }, (err) => {
        failure(err.response)
      })
  }
}