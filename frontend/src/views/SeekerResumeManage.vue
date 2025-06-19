<template>
  <div>
    <el-card>
      <div slot="header" class="clearfix">
        <span>简历管理</span>
        <el-button type="primary" size="mini" @click="openAddDialog" style="float: right;">新增简历</el-button>
      </div>

      <el-table :data="resumes" style="width: 100%">
        <el-table-column prop="title" label="标题" width="180" />
        <el-table-column prop="experience_summary" label="经验总结" />
        <el-table-column prop="skills" label="技能" width="180">
          <template slot-scope="scope">
            {{ formatJsonArray(scope.row.skills) }}
          </template>
        </el-table-column>
        <el-table-column prop="is_default" label="默认简历" width="100">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.is_default">默认</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="240">
          <template slot-scope="scope">
            <el-button size="mini" @click="openEditDialog(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="deleteResume(scope.row.id)">删除</el-button>
            <el-button
                size="mini"
                type="warning"
                v-if="!scope.row.is_default"
                @click="setDefault(scope.row.id)"
            >设为默认</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加/编辑简历 Dialog -->
    <el-dialog :title="form.id ? '编辑简历' : '新增简历'" :visible.sync="dialogVisible">
      <el-form :model="form" label-width="100px">
        <el-form-item label="标题">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="经验总结">
          <el-input type="textarea" v-model="form.experience_summary" />
        </el-form-item>
        <el-form-item label="教育经历（JSON）">
          <el-input type="textarea" v-model="form.education_details" />
        </el-form-item>
        <el-form-item label="技能（JSON）">
          <el-input type="textarea" v-model="form.skills" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitResume">提交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import api from '@/util/api';
import axios from 'axios';

export default {
  name: 'ResumeManager',
  data() {
    return {
      resumes: [],
      dialogVisible: false,
      form: {
        id: null,
        title: '',
        experience_summary: '',
        education_details: '',
        skills: ''
      }
    };
  },
  mounted() {
    this.fetchResumes();
  },
  methods: {
    getJobSeekerId() {
      return localStorage.getItem('jobSeekerId');
    },
    async fetchResumes() {
      try {
        const res = await api.get(`/resumes/jobseeker/${this.getJobSeekerId()}`);
        this.resumes = res.data;
      } catch (err) {
        this.$message.error('获取简历失败');
      }
    },
    openAddDialog() {
      this.form = {
        id: null,
        title: '',
        experience_summary: '',
        education_details: '',
        skills: ''
      };
      this.dialogVisible = true;
    },
    openEditDialog(row) {
      this.form = {
        ...row,
        education_details: JSON.stringify(row.education_details),
        skills: JSON.stringify(row.skills)
      };
      this.dialogVisible = true;
    },
    async submitResume() {
      try {
        const token = localStorage.getItem('token');
        const payload = {
          ...this.form,
          job_seeker_id: this.getJobSeekerId(),
          education_details: JSON.parse(this.form.education_details),
          skills: JSON.parse(this.form.skills)
        };

        if (this.form.id) {
          await axios.put(`/resumes/${this.form.id}`, payload, {
            headers: { Authorization: `Bearer ${token}` }
          });
          this.$message.success('更新成功');
        } else {
          await axios.post(`/resumes`, payload, {
            headers: { Authorization: `Bearer ${token}` }
          });
          this.$message.success('新增成功');
        }

        this.dialogVisible = false;
        this.fetchResumes();
      } catch (err) {
        if (err.response && err.response.status === 403) {
          this.$message.error('没有权限执行该操作，请检查登录身份');
        } else if (err.response) {
          this.$message.error(`提交失败: ${err.response.data.message || '未知错误'}`);
        } else {
          this.$message.error('提交失败，请检查 JSON 格式或网络问题');
        }
        console.error('提交简历失败:', err);
      }
    },
    async deleteResume(id) {
      try {
        await api.delete(`/resumes/${id}`);
        this.$message.success('删除成功');
        this.fetchResumes();
      } catch (err) {
        this.$message.error('删除失败');
      }
    },
    async setDefault(id) {
      try {
        await api.post(`/resumes/${id}/set-default`);
        this.$message.success('设置成功');
        this.fetchResumes();
      } catch (err) {
        this.$message.error('设置失败');
      }
    },
    formatJsonArray(jsonData) {
      if (Array.isArray(jsonData)) {
        return jsonData.join(', ');
      } else if (typeof jsonData === 'object') {
        return Object.values(jsonData).join(', ');
      }
      return '';
    }
  }
};
</script>

<style scoped>
.el-card {
  margin: 20px;
}
</style>
