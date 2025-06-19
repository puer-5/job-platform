<template>
  <div class="dashboard">
    <h1>管理员仪表板</h1>
    <p>欢迎回来，{{ username }}</p>
  </div>
</template>

<script>
export default {
  name: 'AdminDashboard',
  data() {
    return {
      username: ''
    }
  },
  created() {
    const user = JSON.parse(localStorage.getItem('user'))
    if (user) {
      this.username = user.username
    }
  }
}
</script>

<style scoped>
.dashboard {
  padding: 20px;
}
</style> 