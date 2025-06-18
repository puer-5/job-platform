import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'

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
    path: '/jobseeker/dashboard',
    name: 'JobseekerDashboard',
    component: () => import('../views/JobseekerDashboard.vue'),
    meta: { requiresAuth: true, role: 'JOB_SEEKER' }
  },
  {
    path: '/employer/dashboard',
    name: 'EmployerDashboard',
    component: () => import('../views/EmployerDashboard.vue'),
    meta: { requiresAuth: true, role: 'EMPLOYER' }
  },
  {
    path: '/admin/dashboard',
    name: 'AdminDashboard',
    component: () => import('../views/AdminDashboard.vue'),
    meta: { requiresAuth: true, role: 'ADMIN' }
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