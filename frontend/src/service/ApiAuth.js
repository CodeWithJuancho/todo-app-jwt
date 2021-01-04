import { AXIOS } from 'src/service/Api'

export default {

  login(userName, password, success, failure) {
    return AXIOS.post('login/users/token', {
      userName: userName,
      password: password
    })
      .then((response) => {
          success(response.data)
        },
        (err) => {
          failure(err.response)
        })
  }

}