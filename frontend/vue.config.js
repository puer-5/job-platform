module.exports = {
  lintOnSave: false,
  devServer: {
    port: 8080,
    proxy: {
      '^/auth': {
        target: 'http://localhost:9000/auth',
        changeOrigin: true
      },
      '^/jobseeker': {
        target: 'http://localhost:9001',
        changeOrigin: true
      },
      '^/resumes': {
        target: 'http://localhost:9001',
        changeOrigin: true
      },
      '^/jobseekers': {
        target: 'http://localhost:9001',
        changeOrigin: true
      }
    }
  }
} 