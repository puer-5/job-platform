import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'

Vue.config.productionTip = false

// 配置axios默认值
axios.defaults.headers.common['Content-Type'] = 'application/json'

// 从localStorage中恢复token
const token = localStorage.getItem('token')
if (token) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
}

new Vue({
  router,
  render: h => h(App)
}).$mount('#app') 