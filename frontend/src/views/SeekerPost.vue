<template>
  <div class="job-post-container">
    <h2>招聘信息</h2>
    
    <!-- 搜索和筛选区域 -->
    <div class="search-section">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input
            v-model="searchForm.keyword"
            placeholder="搜索职位名称或公司名称"
            clearable
            @keyup.enter.native="searchJobs"
          >
            <i slot="prefix" class="el-icon-search"></i>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchForm.location" placeholder="工作地点" clearable>
            <el-option label="北京" value="北京"></el-option>
            <el-option label="上海" value="上海"></el-option>
            <el-option label="广州" value="广州"></el-option>
            <el-option label="深圳" value="深圳"></el-option>
            <el-option label="杭州" value="杭州"></el-option>
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchForm.salary" placeholder="薪资范围" clearable>
            <el-option label="5k以下" value="0-5000"></el-option>
            <el-option label="5k-10k" value="5000-10000"></el-option>
            <el-option label="10k-20k" value="10000-20000"></el-option>
            <el-option label="20k-30k" value="20000-30000"></el-option>
            <el-option label="30k以上" value="30000-999999"></el-option>
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="searchJobs">搜索</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-col>
      </el-row>
    </div>

    <!-- 招聘信息列表 -->
    <div class="job-list">
      <el-card v-for="job in jobPosts" :key="job.id" class="job-card" shadow="hover">
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
        
        <div class="job-description">
          {{ job.description }}
        </div>
        
        <div class="job-tags">
          <el-tag v-for="tag in job.tags" :key="tag" size="small" style="margin-right: 5px;">
            {{ tag }}
          </el-tag>
        </div>
        
        <div class="job-actions">
          <el-button type="primary" size="small" @click="applyJob(job)">
            立即投递
          </el-button>
          <el-button size="small" @click="viewJobDetail(job)">
            查看详情
          </el-button>
        </div>
      </el-card>
    </div>

    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :page-sizes="[10, 20, 50]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 投递简历弹窗 -->
    <el-dialog title="投递简历" :visible.sync="applyDialogVisible" width="500px">
      <div v-if="selectedResumes.length === 0" class="no-resume">
        <i class="el-icon-warning"></i>
        <p>您还没有创建简历，请先创建简历</p>
        <el-button type="primary" @click="goToResumeManage">去创建简历</el-button>
      </div>
      <div v-else>
        <p>请选择要投递的简历：</p>
        <el-radio-group v-model="selectedResumeId">
          <el-radio 
            v-for="resume in selectedResumes" 
            :key="resume.id" 
            :label="resume.id"
            class="resume-option"
          >
            <div class="resume-info">
              <div class="resume-title">{{ resume.title }}</div>
              <div class="resume-summary">{{ resume.summary }}</div>
            </div>
          </el-radio>
        </el-radio-group>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="applyDialogVisible = false">取消</el-button>
        <el-button 
          type="primary" 
          @click="confirmApply"
          :disabled="!selectedResumeId"
        >
          确认投递
        </el-button>
      </span>
    </el-dialog>

    <!-- 职位详情弹窗 -->
    <el-dialog title="职位详情" :visible.sync="detailDialogVisible" width="800px">
      <div v-if="selectedJob" class="job-detail">
        <div class="detail-header">
          <h2>{{ selectedJob.title }}</h2>
          <div class="detail-salary">{{ selectedJob.salary }}</div>
        </div>
        
        <div class="detail-company">
          <h3>{{ selectedJob.companyName }}</h3>
          <p>{{ selectedJob.companyDescription }}</p>
        </div>
        
        <div class="detail-info">
          <el-descriptions :column="2" border>
            <el-descriptions-item label="工作地点">{{ selectedJob.location }}</el-descriptions-item>
            <el-descriptions-item label="工作经验">{{ selectedJob.experience }}</el-descriptions-item>
            <el-descriptions-item label="学历要求">{{ selectedJob.education }}</el-descriptions-item>
            <el-descriptions-item label="招聘人数">{{ selectedJob.headcount }}</el-descriptions-item>
            <el-descriptions-item label="发布时间">{{ selectedJob.createTime }}</el-descriptions-item>
            <el-descriptions-item label="截止时间">{{ selectedJob.deadline }}</el-descriptions-item>
          </el-descriptions>
        </div>
        
        <div class="detail-description">
          <h3>职位描述</h3>
          <div v-html="selectedJob.description"></div>
        </div>
        
        <div class="detail-requirements">
          <h3>任职要求</h3>
          <div v-html="selectedJob.requirements"></div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'SeekerPost',
  data() {
    return {
      jobPosts: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      searchForm: {
        keyword: '',
        location: '',
        salary: ''
      },
      applyDialogVisible: false,
      detailDialogVisible: false,
      selectedJob: null,
      selectedResumes: [],
      selectedResumeId: null,
      jobSeekerId: null
    };
  },
  mounted() {
    this.jobSeekerId = JSON.parse(atob(localStorage.getItem('token').split('.')[1])).userId;
    this.fetchJobPosts();
  },
  methods: {
    async fetchJobPosts() {
      try {
        const params = {
          page: this.currentPage - 1,
          size: this.pageSize,
          ...this.searchForm
        };
        const response = await axios.get('/job-posts', { params });
        this.jobPosts = response.data.content;
        this.total = response.data.totalElements;
      } catch (error) {
        console.error('获取招聘信息失败:', error);
        this.$message.error('获取招聘信息失败');
      }
    },
    
    searchJobs() {
      this.currentPage = 1;
      this.fetchJobPosts();
    },
    
    resetSearch() {
      this.searchForm = {
        keyword: '',
        location: '',
        salary: ''
      };
      this.currentPage = 1;
      this.fetchJobPosts();
    },
    
    handleSizeChange(val) {
      this.pageSize = val;
      this.fetchJobPosts();
    },
    
    handleCurrentChange(val) {
      this.currentPage = val;
      this.fetchJobPosts();
    },
    
    async applyJob(job) {
      this.selectedJob = job;
      try {
        // 获取用户的简历列表
        const response = await axios.get(`/resumes/jobseeker/${this.jobSeekerId}`);
        this.selectedResumes = response.data;
        
        if (this.selectedResumes.length === 0) {
          this.$message.warning('请先创建简历');
          this.applyDialogVisible = true;
        } else {
          this.selectedResumeId = this.selectedResumes[0].id;
          this.applyDialogVisible = true;
        }
      } catch (error) {
        console.error('获取简历失败:', error);
        this.$message.error('获取简历失败');
      }
    },
    
    async confirmApply() {
      try {
        const application = {
          jobPostId: this.selectedJob.id,
          jobSeekerId: this.jobSeekerId,
          resumeId: this.selectedResumeId,
          status: 'PENDING'
        };
        
        await axios.post('/job-applications', application);
        this.$message.success('投递成功！');
        this.applyDialogVisible = false;
        this.selectedResumeId = null;
      } catch (error) {
        console.error('投递失败:', error);
        this.$message.error('投递失败，请重试');
      }
    },
    
    viewJobDetail(job) {
      this.selectedJob = job;
      this.detailDialogVisible = true;
    },
    
    goToResumeManage() {
      this.applyDialogVisible = false;
      this.$router.push('/jobseeker/seeker-resume');
    }
  }
};
</script>

<style scoped>
.job-post-container {
  padding: 20px;
}

.search-section {
  background: #f5f7fa;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
}

.job-list {
  margin-bottom: 20px;
}

.job-card {
  margin-bottom: 15px;
  transition: all 0.3s ease;
}

.job-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.job-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 10px;
}

.job-title {
  display: flex;
  align-items: center;
  gap: 10px;
}

.job-title h3 {
  margin: 0;
  color: #303133;
  font-size: 18px;
}

.job-salary {
  color: #f56c6c;
  font-weight: bold;
  font-size: 16px;
}

.job-company {
  color: #606266;
  margin-bottom: 10px;
  font-size: 14px;
}

.job-info {
  display: flex;
  gap: 20px;
  margin-bottom: 10px;
  color: #909399;
  font-size: 13px;
}

.job-info span {
  display: flex;
  align-items: center;
  gap: 5px;
}

.job-description {
  color: #606266;
  margin-bottom: 10px;
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.job-tags {
  margin-bottom: 15px;
}

.job-actions {
  display: flex;
  gap: 10px;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.no-resume {
  text-align: center;
  padding: 40px 0;
}

.no-resume i {
  font-size: 48px;
  color: #e6a23c;
  margin-bottom: 20px;
}

.resume-option {
  display: block;
  margin-bottom: 15px;
  padding: 15px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  width: 100%;
}

.resume-option:hover {
  border-color: #409eff;
}

.resume-info {
  margin-left: 10px;
}

.resume-title {
  font-weight: bold;
  color: #303133;
  margin-bottom: 5px;
}

.resume-summary {
  color: #909399;
  font-size: 13px;
}

.job-detail {
  max-height: 600px;
  overflow-y: auto;
}

.detail-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #ebeef5;
}

.detail-header h2 {
  margin: 0;
  color: #303133;
}

.detail-salary {
  color: #f56c6c;
  font-weight: bold;
  font-size: 18px;
}

.detail-company {
  margin-bottom: 20px;
  padding: 15px;
  background: #f5f7fa;
  border-radius: 4px;
}

.detail-company h3 {
  margin: 0 0 10px 0;
  color: #303133;
}

.detail-company p {
  margin: 0;
  color: #606266;
  line-height: 1.5;
}

.detail-info {
  margin-bottom: 20px;
}

.detail-description,
.detail-requirements {
  margin-bottom: 20px;
}

.detail-description h3,
.detail-requirements h3 {
  margin-bottom: 10px;
  color: #303133;
  font-size: 16px;
}

.detail-description div,
.detail-requirements div {
  color: #606266;
  line-height: 1.6;
}
</style>