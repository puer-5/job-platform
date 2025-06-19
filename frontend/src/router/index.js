import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import JobseekerHome from '../views/JobseekerHome.vue'
import SeekerResumeManage from '@/views/SeekerResumeManage.vue'
import SeekerPost from "@/views/SeekerPost.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path:'/jobseeker',
    component: ()=> import('@/components/JobseekerContainer.vue'),
    redirect: '/jobseeker/home',
    children:[
      {
        path: 'home', // 求职者主页面
        name: 'JobseekerHome',
        component: JobseekerHome,
        meta: { requiresAuth: true },
      },
      {
        path: 'seeker-resume', // 求职者简历管理页面
        name: 'resumeManage',
        component: SeekerResumeManage,
        meta: { requiresAuth: true },
      },
      {
        path: 'resume-post', // 求职者招聘投递页面
        name: 'SeekerPost',
        component: SeekerPost,
        meta: { requiresAuth: true },
      },
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const userType = localStorage.getItem('userType')

  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!token) {
      next('/login')
    } else if (to.meta.role && to.meta.role !== userType) {
      next('/login')
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router 