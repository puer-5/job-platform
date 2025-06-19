module.exports = {
  lintOnSave: false,
  devServer: {
    port: 8080,
    proxy: {
      '^/auth': {
        target: 'http://localhost:9000',
        changeOrigin: true
      },
      '^/jobseeker': {
        target: 'http://localhost:9000',
        changeOrigin: true
      },
      '^/resumes': {
        target: 'http://localhost:9000',
        changeOrigin: true
      },
      '^/jobseekers': {
        target: 'http://localhost:9000',
        changeOrigin: true
      }
    }
  }
} 