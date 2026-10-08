import Vue from 'vue'
import ElementUI from 'element-ui';
import App from './App.vue'
import router from './router'
import store from './store'
import * as echarts from 'echarts'
Vue.prototype.$echarts = echarts;
Vue.prototype.$baseUrl = 'http://47.98.102.26:9090'
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/css/global.css'
import '@/assets/css/theme/index.css'

Vue.config.productionTip = false
Vue.use(ElementUI);
new Vue({
  router,
  store,
  render: h => h(App),

}).$mount('#app')




