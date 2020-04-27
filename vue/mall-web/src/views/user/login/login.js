
import 'babel-polyfill'
import 'font-awesome/css/font-awesome.min.css';
import Vue from 'vue'
import App from './loginApp'
import vmodal from 'vue-js-modal'

// ========= 全局消息设置
var Bus = new Vue();

Vue.use(vmodal)

new Vue({
  render: h => h(App)
  ,data: {
      Bus
  }
}).$mount('#app')
