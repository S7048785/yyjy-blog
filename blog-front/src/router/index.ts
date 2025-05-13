import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/views/index.vue"
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: Home,
      redirect: '/article',
      children: [
        {
          path: 'article',
          name: 'article',
          component: () => import('@/views/article/index.vue'),
          meta: {
            title: 'Article - 陈九'
          }
        },
        {
          path: '/article/:id',
          component: () => import('@/views/article/detail.vue'),
        },
        {
          path: 'category',
          name: 'category',
          component: () => import('@/views/category/index.vue'),
          meta: {
            title: 'Category - 陈九'
          }
        },
        {
          path: 'animation',
          name: 'animation',
          component: () => import('@/views/animation/index.vue'),
          meta: {
            title: 'Animation - 陈九'
          }
        },
        {
          path: 'about',
          name: 'about',
          component: () => import('@/views/about/index.vue'),
          meta: {
            title: 'About - 陈九'
          }
        }
      ]
    }
  ],
})

router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = to.meta.title as string;
  }
  next();
})

export default router
