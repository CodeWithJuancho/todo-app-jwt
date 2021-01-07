const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/Index.vue') },
      {
        path: '/login',
        name: 'login',
        component: () => import('src/components/Login.vue')
      },
      {
        path: '/signup',
        name: 'signup',
        component: () => import('src/components/SignUp.vue')
      }
    ]
  },
  {
    path: '/admin',
    name: 'admin',
    component: () => import('pages/admin/Admin.vue')
  },
  {
    path: '/user',
    name: 'user',
    component: () => import('pages/user/User.vue')
  },
  {
    path: '/customer',
    name: 'customer',
    component: () => import('pages/customer/Customer.vue')
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '*',
    component: () => import('pages/Error404.vue')
  }
]

export default routes
