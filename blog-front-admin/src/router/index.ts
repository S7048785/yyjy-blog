import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import {useRouterStore} from "@/stores/router.ts";

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes: [
		{
			path: '/',
			component: HomeView,
			redirect: '/home',
			children: [
				{
					path: '/home',
					name: 'home',
					component: () => import('../views/home/Index.vue'),
					meta: {
						title: '首页'
					}
				},
				{
					path: '/article',
					name: 'article',
					component: () => import('../views/article/Index.vue'),
					meta: {
						title: '文章管理'
					}
				},
				{
					path: '/category',
					name: 'category',
					component: () => import('../views/category/Index.vue'),
					meta: {
						title: '分类管理'
					}
				},
				{
					path: '/tag',
					name: 'tag',
					component: () => import('../views/tag/Index.vue'),
					meta: {
						title: '标签管理'
					}
				},
				{
					path: '/comment',
					name: 'comment',
					component: () => import('../views/comment/Index.vue'),
					meta: {
						title: '评论管理'
					}
				},
			]
		}
	],
})

router.beforeEach((to, from, next) => {
	document.title = to.meta.title as string
	next()
})

export default router
