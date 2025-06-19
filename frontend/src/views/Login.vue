<template>
  <div class="login-container">
    <div class="login-box">
      <h2>求职平台登录</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="username">用户名</label>
          <input
            type="text"
            id="username"
            v-model="loginForm.username"
            required
            placeholder="请输入用户名"
          />
        </div>
        <div class="form-group">
          <label for="password">密码</label>
          <input
            type="password"
            id="password"
            v-model="loginForm.password"
            required
            placeholder="请输入密码"
          />
        </div>
        <div class="form-group">
          <button type="submit" class="login-btn" :disabled="loading">
            {{ loading ? '登录中...' : '登录' }}
          </button>
        </div>
        <div v-if="errorMessage" class="error-message">
          {{ errorMessage }}
        </div>
      </form>
      <div class="test-users">
        <h3>测试账号</h3>
        <div class="user-list">
          <div v-for="(user, index) in testUsers" :key="index" class="user-item" @click="useTestUser(user)">
            <p><strong>用户名：</strong>{{ user.username }}</p>
            <p><strong>密码：</strong>{{ user.password }}</p>
            <p><strong>类型：</strong>{{ user.userType }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loading: false,
      errorMessage: '',
      testUsers: [
        {
          username: 'jobseeker1',
          password: '123456',
          userType: 'USER'
        },
        {
          username: 'jobseeker2',
          password: '123456',
          userType: 'USER'
        },
        {
          username: 'employer1',
          password: '123456',
          userType: 'ENTERPRISE'
        },
        {
          username: 'employer2',
          password: '123456',
          userType: 'ENTERPRISE'
        },
        {
          username: 'admin',
          password: '123456',
          userType: 'ADMIN'
        }
      ]
    }
  },
  methods: {
    useTestUser(user) {
      this.loginForm.username = user.username
      this.loginForm.password = user.password
    },
    async handleLogin() {
      this.loading = true
      this.errorMessage = ''
      
      try {
        // 创建登录数据对象，确保数据格式正确
        const loginData = {
          username: this.loginForm.username.trim(),
          password: this.loginForm.password.trim()
        };
        
        console.log('Sending login request with data:', loginData);
        
        const response = await axios.post('http://localhost:9000/auth/auth/login', loginData).then(
            response => {
              console.log(response.data)
              return response
            }
        ).catch(error => {
          console.log(error)
          throw error
        });
        
        console.log('Login response:', response.data);
        
        // 检查响应中是否包含 token
        if (response.data && response.data.token) {
          // 从 token 中解析用户类型
          const token = response.data.token;
          const tokenData = JSON.parse(atob(token.split('.')[1]));
          const userType = tokenData.userType;
          
          console.log('Token data:', tokenData);
          
          // 保存 token 和用户类型
          localStorage.setItem('token', token);
          localStorage.setItem('userType', userType);
          
          // 设置 axios 默认请求头
          axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
          
          // 根据用户类型跳转到不同页面
          if (userType === 'USER') {
            await this.$router.push('/jobseeker/home');
          } else if (userType === 'ENTERPRISE') {
            // 暂时跳转到求职者页面，因为还没有雇主页面
            await this.$router.push('/jobseeker/home');
          } else if (userType === 'ADMIN') {
            // 暂时跳转到求职者页面，因为还没有管理员页面
            await this.$router.push('/jobseeker/home');
          } else {
            this.errorMessage = '未知的用户类型';
          }
        } else {
          this.errorMessage = '登录响应格式不正确';
        }
      } catch (error) {
        console.error('Login error:', error);
        console.error('Request data:', this.loginForm);
        if (error.response) {
          console.error('Error response:', error.response.data);
          this.errorMessage = error.response.data.message || '登录失败，请检查用户名和密码';
        } else {
          this.errorMessage = '登录失败，请稍后重试';
        }
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.login-box {
  width: 400px;
  padding: 40px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  color: #333;
  margin-bottom: 30px;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 5px;
  color: #666;
}

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

input:focus {
  outline: none;
  border-color: #409eff;
}

.login-btn {
  width: 100%;
  padding: 12px;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.login-btn:hover:not(:disabled) {
  background-color: #66b1ff;
}

.login-btn:disabled {
  background-color: #a0cfff;
  cursor: not-allowed;
}

.error-message {
  color: #f56c6c;
  text-align: center;
  margin-top: 10px;
  font-size: 14px;
}

.test-users {
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.test-users h3 {
  text-align: center;
  color: #666;
  margin-bottom: 15px;
}

.user-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.user-item {
  padding: 10px;
  background: #f8f9fa;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.user-item:hover {
  background: #e9ecef;
}

.user-item p {
  margin: 5px 0;
  font-size: 14px;
  color: #666;
}
</style> 