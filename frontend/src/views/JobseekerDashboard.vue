<template>
  <div class="dashboard">
    <h1>求职者仪表板</h1>
    <p>欢迎回来，{{ username }}</p>
  </div>
</template>

<script>
export default {
  name: 'JobseekerDashboard',
  data() {
    return {
      username: ''
    }
  },
  created() {
    const token = localStorage.getItem('token')
    if (token) {
      const tokenData = JSON.parse(atob(token.split('.')[1]))
      this.username = tokenData.username
    }
  }
}
</script>

<style scoped>
.dashboard {
  padding: 20px;
}
</style> 