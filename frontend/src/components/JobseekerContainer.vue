<template>
  <el-container style="height: 100vh;">
    <!-- 头部 -->
    <el-header class="header">
      <div class="header-content">
        <span class="header-title">求职者信息系统</span>
        <el-button type="text" class="logout-button" @click="handleLogout">注销</el-button>
      </div>
    </el-header>

    <!-- 主体 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="200px" class="aside">
        <el-menu :default-active="activeMenu" @select="handleMenuSelect" class="custom-menu">
          <el-menu-item index="home">
            <i class="el-icon-house"></i> 首页
          </el-menu-item>
          <el-menu-item index="seeker-resume">
            <i class="el-icon-document"></i> 简历管理
          </el-menu-item>
          <el-menu-item index="resume-post">
            <i class="el-icon-upload"></i> 招聘投递
          </el-menu-item>
        </el-menu>
      </el-aside>

      <!-- 内容区域 -->
      <el-main class="main-content">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'JobSeekerLayout',
  data() {
    return {
      activeMenu: 'home',
    };
  },
  watch: {
    // 监听路由变化，更新菜单激活状态
    '$route'(to) {
      this.updateActiveMenu(to.path);
    }
  },
  mounted() {
    // 初始化时设置正确的菜单激活状态
    this.updateActiveMenu(this.$route.path);
  },
  methods: {
    updateActiveMenu(path) {
      // 根据路径设置激活的菜单项
      if (path.includes('home')) {
        this.activeMenu = 'home';
      } else if (path.includes('seeker-resume')) {
        this.activeMenu = 'seeker-resume';
      } else if (path.includes('resume-post')) {
        this.activeMenu = 'resume-post';
      } else {
        this.activeMenu = 'home';
      }
    },
    handleMenuSelect(index) {
      this.activeMenu = index;
      this.$router.push(`/jobseeker/${index}`);
    },
    handleLogout() {
      localStorage.removeItem('token');
      localStorage.removeItem('userType');
      this.$router.push('/login');
    },
  },
};
</script>

<style scoped>
/* 头部样式 */
.header {
  background-color: #343a40;
  color: #ffffff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  height: 60px;
}
.header-content {
  display: flex;
  align-items: center;
  width: 100%;
}
.header-title {
  font-size: 18px;
  font-weight: 500;
}
.logout-button {
  color: #ffffff;
  font-size: 14px;
  font-weight: 400;
  margin-left: auto;
}
.logout-button:hover {
  color: #e57373;
}

/* 侧边栏样式 */
.aside {
  background-color: #f8f9fa;
  color: #343a40;
  height: 100%;
  border-right: 1px solid #dee2e6;
  box-shadow: 1px 0 5px rgba(0, 0, 0, 0.1);
}

/* 菜单样式 */
.custom-menu {
  background-color: transparent;
  border: none;
}
.custom-menu .el-menu-item {
  color: #495057;
  font-size: 14px;
  padding: 12px 20px;
  border-radius: 4px;
  transition: all 0.2s ease-in-out;
}
.custom-menu .el-menu-item.is-active {
  background-color: #e9ecef;
  color: #212529;
  font-weight: 500;
}
.custom-menu .el-menu-item:hover {
  background-color: #f1f3f5;
  color: #212529;
  cursor: pointer;
}
.custom-menu .el-icon-house,
.custom-menu .el-icon-document,
.custom-menu .el-icon-upload {
  margin-right: 10px;
  font-size: 16px;
  color: #868e96;
}

/* 主内容区域 */
.main-content {
  background-color: #ffffff;
  padding: 20px;
  height: 100%;
  overflow-y: auto;
}
</style>