module.exports = {
  lintOnSave: false,
  devServer: {
    port: 8080,
    proxy: {
      '/auth': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        pathRewrite: {
          '^/auth': '/auth'
        },
        logLevel: 'debug'
      },
      '^/jobseeker': {
        target: 'http://localhost:9000',
        changeOrigin: true
      }
    }
  }
} 