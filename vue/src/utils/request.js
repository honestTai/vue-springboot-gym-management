import axios from 'axios'
import Vue from "vue";

const service = axios.create({
    baseURL:'http://47.98.102.26:9090',
    timeout:3000
})

export default service
