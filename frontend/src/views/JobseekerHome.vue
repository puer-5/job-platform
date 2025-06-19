<template>
  <div class="jobseeker-home">
    <!-- 欢迎区域 -->
    <div class="welcome-section">
      <div class="welcome-content">
        <h1>欢迎回来，{{ userInfo.name || '求职者' }}！</h1>
        <p>今天是 {{ currentDate }}，祝您求职顺利！</p>
      </div>
      <div class="welcome-avatar">
        <i class="el-icon-user-solid"></i>
      </div>
    </div>

    <!-- 统计卡片 -->
    <div class="stats-section">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-card class="stat-card" shadow="hover">
            <div class="stat-content">
              <div class="stat-icon resume-icon">
                <i class="el-icon-document"></i>
              </div>
              <div class="stat-info">
                <div class="stat-number">{{ stats.resumeCount }}</div>
                <div class="stat-label">我的简历</div>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stat-card" shadow="hover">
            <div class="stat-content">
              <div class="stat-icon apply-icon">
                <i class="el-icon-upload"></i>
              </div>
              <div class="stat-info">
                <div class="stat-number">{{ stats.applyCount }}</div>
                <div class="stat-label">投递记录</div>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stat-card" shadow="hover">
            <div class="stat-content">
              <div class="stat-icon interview-icon">
                <i class="el-icon-phone"></i>
              </div>
              <div class="stat-info">
                <div class="stat-number">{{ stats.interviewCount }}</div>
                <div class="stat-label">面试邀请</div>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stat-card" shadow="hover">
            <div class="stat-content">
              <div class="stat-icon offer-icon">
                <i class="el-icon-trophy"></i>
              </div>
              <div class="stat-info">
                <div class="stat-number">{{ stats.offerCount }}</div>
                <div class="stat-label">录用通知</div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- 快捷操作 -->
    <div class="quick-actions-section">
      <h2>快捷操作</h2>
      <div class="quick-actions">
        <div class="action-card" @click="goToResumeManage">
          <div class="action-icon">
            <i class="el-icon-edit"></i>
          </div>
          <div class="action-content">
            <h3>管理简历</h3>
            <p>创建、编辑和管理您的简历</p>
          </div>
        </div>
        
        <div class="action-card" @click="goToJobSearch">
          <div class="action-icon">
            <i class="el-icon-search"></i>
          </div>
          <div class="action-content">
            <h3>搜索职位</h3>
            <p>浏览最新招聘信息并投递简历</p>
          </div>
        </div>
        
        <div class="action-card" @click="goToApplications">
          <div class="action-icon">
            <i class="el-icon-s-order"></i>
          </div>
          <div class="action-content">
            <h3>投递记录</h3>
            <p>查看您的投递历史和状态</p>
          </div>
        </div>
        
        <div class="action-card" @click="goToProfile">
          <div class="action-icon">
            <i class="el-icon-user"></i>
          </div>
          <div class="action-content">
            <h3>个人资料</h3>
            <p>更新您的个人信息和偏好</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 最近投递记录 -->
    <div class="recent-applications-section">
      <div class="section-header">
        <h2>最近投递记录</h2>
        <el-button type="text" @click="goToApplications">查看全部</el-button>
      </div>
      
      <div v-if="recentApplications.length === 0" class="empty-applications">
        <i class="el-icon-s-order"></i>
        <p>您还没有投递过简历</p>
        <el-button type="primary" @click="goToJobSearch">立即投递</el-button>
      </div>
      
      <div v-else class="applications-list">
        <el-card v-for="application in recentApplications" :key="application.id" class="application-card" shadow="hover">
          <div class="application-header">
            <div class="job-info">
              <h3>{{ application.jobTitle }}</h3>
              <p class="company-name">{{ application.companyName }}</p>
            </div>
            <div class="application-status">
              <el-tag :type="getStatusType(application.status)">
                {{ getStatusText(application.status) }}
              </el-tag>
            </div>
          </div>
          
          <div class="application-details">
            <div class="detail-item">
              <i class="el-icon-time"></i>
              <span>投递时间：{{ formatDate(application.applyTime) }}</span>
            </div>
            <div class="detail-item">
              <i class="el-icon-document"></i>
              <span>使用简历：{{ application.resumeTitle }}</span>
            </div>
          </div>
          
          <div class="application-actions">
            <el-button size="small" @click="viewJobDetail(application.jobId)">
              查看职位
            </el-button>
            <el-button size="small" type="text" @click="viewApplicationDetail(application.id)">
              查看详情
            </el-button>
          </div>
        </el-card>
      </div>
    </div>

    <!-- 推荐职位 -->
    <div class="recommended-jobs-section">
      <div class="section-header">
        <h2>推荐职位</h2>
        <el-button type="text" @click="goToJobSearch">查看更多</el-button>
      </div>
      
      <div v-if="recommendedJobs.length === 0" class="empty-jobs">
        <i class="el-icon-briefcase"></i>
        <p>暂无推荐职位</p>
      </div>
      
      <div v-else class="jobs-list">
        <el-card v-for="job in recommendedJobs" :key="job.id" class="job-card" shadow="hover">
          <div class="job-header">
            <div class="job-title">
              <h3>{{ job.title }}</h3>
              <el-tag v-if="job.isUrgent" type="danger" size="small">急招</el-tag>
            </div>
            <div class="job-salary">{{ job.salary }}</div>
          </div>
          
          <div class="job-company">
            <i class="el-icon-office-building"></i>
            {{ job.companyName }}
          </div>
          
          <div class="job-info">
            <span><i class="el-icon-location"></i> {{ job.location }}</span>
            <span><i class="el-icon-time"></i> {{ job.experience }}</span>
            <span><i class="el-icon-reading"></i> {{ job.education }}</span>
          </div>
          
          <div class="job-actions">
            <el-button type="primary" size="small" @click="applyJob(job)">
              立即投递
            </el-button>
            <el-button size="small" @click="viewJobDetail(job.id)">
              查看详情
            </el-button>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'JobseekerHome',
  data() {
    return {
      userInfo: {},
      currentDate: '',
      stats: {
        resumeCount: 0,
        applyCount: 0,
        interviewCount: 0,
        offerCount: 0
      },
      recentApplications: [],
      recommendedJobs: [],
      jobSeekerId: null
    };
  },
  mounted() {
    this.jobSeekerId = JSON.parse(atob(localStorage.getItem('token').split('.')[1])).userId;
    this.currentDate = new Date().toLocaleDateString('zh-CN');
    this.loadData();
  },
  methods: {
    async loadData() {
      await Promise.all([
        this.loadUserInfo(),
        this.loadStats(),
        this.loadRecentApplications(),
        this.loadRecommendedJobs()
      ]);
    },
    
    async loadUserInfo() {
      try {
        const response = await axios.get(`/jobseekers/${this.jobSeekerId}`);
        this.userInfo = response.data;
      } catch (error) {
        console.error('获取用户信息失败:', error);
      }
    },
    
    async loadStats() {
      try {
        // 获取简历数量
        const resumeResponse = await axios.get(`/resumes/jobseeker/${this.jobSeekerId}`);
        this.stats.resumeCount = resumeResponse.data.length;
        
        // 获取投递统计
        const applicationResponse = await axios.get(`/job-applications/jobseeker/${this.jobSeekerId}/stats`);
        this.stats.applyCount = applicationResponse.data.totalCount || 0;
        this.stats.interviewCount = applicationResponse.data.interviewCount || 0;
        this.stats.offerCount = applicationResponse.data.offerCount || 0;
      } catch (error) {
        console.error('获取统计数据失败:', error);
      }
    },
    
    async loadRecentApplications() {
      try {
        const response = await axios.get(`/job-applications/jobseeker/${this.jobSeekerId}/recent`);
        this.recentApplications = response.data;
      } catch (error) {
        console.error('获取最近投递记录失败:', error);
      }
    },
    
    async loadRecommendedJobs() {
      try {
        const response = await axios.get('/job-posts/recommended', {
          params: { jobSeekerId: this.jobSeekerId, limit: 6 }
        });
        this.recommendedJobs = response.data;
      } catch (error) {
        console.error('获取推荐职位失败:', error);
      }
    },
    
    getStatusType(status) {
      const statusMap = {
        'PENDING': 'warning',
        'REVIEWING': 'primary',
        'INTERVIEW': 'success',
        'OFFER': 'success',
        'REJECTED': 'danger'
      };
      return statusMap[status] || 'info';
    },
    
    getStatusText(status) {
      const statusMap = {
        'PENDING': '待处理',
        'REVIEWING': '审核中',
        'INTERVIEW': '面试邀请',
        'OFFER': '录用通知',
        'REJECTED': '已拒绝'
      };
      return statusMap[status] || status;
    },
    
    formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      return date.toLocaleDateString('zh-CN');
    },
    
    goToResumeManage() {
      this.$router.push('/jobseeker/seeker-resume');
    },
    
    goToJobSearch() {
      this.$router.push('/jobseeker/resume-post');
    },
    
    goToApplications() {
      // 这里可以跳转到投递记录页面，如果还没有这个页面，可以暂时跳转到招聘页面
      this.$router.push('/jobseeker/resume-post');
    },
    
    goToProfile() {
      // 这里可以跳转到个人资料页面
      this.$message.info('个人资料功能开发中...');
    },
    
    viewJobDetail(jobId) {
      // 这里可以跳转到职位详情页面
      this.$message.info('职位详情功能开发中...');
    },
    
    viewApplicationDetail(applicationId) {
      // 这里可以跳转到投递详情页面
      this.$message.info('投递详情功能开发中...');
    },
    
    applyJob(job) {
      // 跳转到招聘页面并传递职位信息
      this.$router.push({
        path: '/jobseeker/resume-post',
        query: { jobId: job.id }
      });
    }
  }
};
</script>

<style scoped>
.jobseeker-home {
  padding: 20px;
}

.welcome-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 30px;
  border-radius: 12px;
  margin-bottom: 30px;
}

.welcome-content h1 {
  margin: 0 0 10px 0;
  font-size: 28px;
  font-weight: 600;
}

.welcome-content p {
  margin: 0;
  font-size: 16px;
  opacity: 0.9;
}

.welcome-avatar {
  width: 80px;
  height: 80px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.welcome-avatar i {
  font-size: 40px;
  color: white;
}

.stats-section {
  margin-bottom: 30px;
}

.stat-card {
  height: 120px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-5px);
}

.stat-content {
  display: flex;
  align-items: center;
  height: 100%;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
}

.stat-icon i {
  font-size: 24px;
  color: white;
}

.resume-icon {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.apply-icon {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.interview-icon {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.offer-icon {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}

.stat-info {
  flex: 1;
}

.stat-number {
  font-size: 32px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  color: #909399;
}

.quick-actions-section {
  margin-bottom: 30px;
}

.quick-actions-section h2 {
  margin-bottom: 20px;
  color: #303133;
}

.quick-actions {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
}

.action-card {
  background: white;
  border: 1px solid #ebeef5;
  border-radius: 8px;
  padding: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.action-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-color: #409eff;
}

.action-icon {
  width: 50px;
  height: 50px;
  background: #f0f9ff;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
}

.action-icon i {
  font-size: 24px;
  color: #409eff;
}

.action-content h3 {
  margin: 0 0 5px 0;
  color: #303133;
  font-size: 16px;
}

.action-content p {
  margin: 0;
  color: #909399;
  font-size: 13px;
}

.recent-applications-section,
.recommended-jobs-section {
  margin-bottom: 30px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-header h2 {
  margin: 0;
  color: #303133;
}

.empty-applications,
.empty-jobs {
  text-align: center;
  padding: 60px 20px;
  color: #909399;
}

.empty-applications i,
.empty-jobs i {
  font-size: 64px;
  margin-bottom: 20px;
  color: #c0c4cc;
}

.empty-applications p,
.empty-jobs p {
  margin: 0 0 20px 0;
  font-size: 16px;
}

.applications-list,
.jobs-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.application-card,
.job-card {
  transition: all 0.3s ease;
}

.application-card:hover,
.job-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.application-header,
.job-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 10px;
}

.job-info h3,
.job-title h3 {
  margin: 0 0 5px 0;
  color: #303133;
  font-size: 16px;
}

.company-name {
  margin: 0;
  color: #606266;
  font-size: 14px;
}

.application-details,
.job-info {
  display: flex;
  gap: 20px;
  margin-bottom: 15px;
  color: #909399;
  font-size: 13px;
}

.detail-item {
  display: flex;
  align-items: center;
  gap: 5px;
}

.job-company {
  color: #606266;
  margin-bottom: 10px;
  font-size: 14px;
}

.application-actions,
.job-actions {
  display: flex;
  gap: 10px;
}
</style> 