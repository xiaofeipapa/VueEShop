
import 'babel-polyfill'
import 'font-awesome/css/font-awesome.min.css';
import Vue from 'vue'
import App from './registerApp'
import vmodal from 'vue-js-modal'


Vue.use(vmodal)

new Vue({
  render: h => h(App)
}).$mount('#app')
