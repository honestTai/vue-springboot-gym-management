const { defineConfig } = require('@vue/cli-service')
module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://47.98.102.26:9090'
      },
    }
  }
}
