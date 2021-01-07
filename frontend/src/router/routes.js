import Auth from 'src/service/Auth'

const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/Index.vue') },
      {
        path: '/login',
        name: 'login',
        component: () => import('src/components/Login.vue'),
        meta: { requiresAuth: false },
        beforeEnter: redirectIfLoggedIn
      },
      {
        path: '/signup',
        name: 'signup',
        component: () => import('src/components/SignUp.vue'),
        meta: { requiresAuth: false },
        beforeEnter: redirectIfLoggedIn
      }
    ]
  },
  {
    path: '/admin',
    name: 'admin',
    component: () => import('pages/admin/Admin.vue'),
    meta: { requiresAuth: true },
    beforeEnter: (to, from, next) => {
      if (Auth.accessLevelAdmin()) {
        next()
      } else {
        next({ name: 'login' })
      }
    }
  },
  {
    path: '/user',
    name: 'user',
    component: () => import('pages/user/User.vue'),
    meta: { requiresAuth: true },
    beforeEnter: (to, from, next) => {
      if (Auth.accessLevelUser() && !Auth.accessLevelAdmin()) {
        next()
      } else {
        next({ name: 'login' })
      }
    }
  },
  {
    path: '/customer',
    name: 'customer',
    component: () => import('pages/customer/Customer.vue'),
    meta: { requiresAuth: true },
    beforeEnter: (to, from, next) => {
      if (Auth.accessLevelCustomer() && !Auth.accessLevelAdmin()) {
        next()
      } else {
        next({ name: 'login' })
      }
    }
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '*',
    component: () => import('pages/Error404.vue')
  }
]

function redirectIfLoggedIn(to, from, next) {
  if (Auth.isLoggedIn() && Auth.accessLevelAdmin()) {
    next({ name: 'admin' })
  } else if (Auth.isLoggedIn() && Auth.accessLevelUser()) {
    next({ name: 'user' })
  } else if (Auth.isLoggedIn() && Auth.accessLevelCustomer()) {
    next({ name: 'customer' })
  } else {
    next()
  }
}

export default routes
