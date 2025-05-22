<script setup lang="ts">
import {
	ReaderOutline as ReaderIcon,
	HomeOutline as HomeIcon,
	PricetagOutline as PricetagIcon,
	ChatboxOutline as ChatboxIcon,
	GridOutline as GridIcon
} from '@vicons/ionicons5'
import {type MenuOption, NIcon} from "naive-ui";
import {RouterLink} from "vue-router";
import {useRouterStore} from "@/stores/router.ts";
const msg = useMessage()
const routerStore = useRouterStore()
const router = useRouter()
const route = useRoute()

/**
 * 跳转路由
 * @param index
 */
function to(index: number) {
	router.replace(routerStore.historyList[index].name)
	routerStore.spliceHistory(index)
}

function renderIcon(icon: Component) {
	return () => h(NIcon, null, {default: () => h(icon)})
}

function renderRouterLink(to: string, name: string) {
	return () => h(RouterLink, {to: {name: to}}, {default: () => name})
}

const menuOptions: MenuOption[] = [
	{
		label: renderRouterLink('home', '首页'),
		key: 'home',
		icon: renderIcon(HomeIcon)
	},
	{
		label: renderRouterLink('article', '文章管理'),
		key: 'article',
		icon: renderIcon(ReaderIcon)
	},
	{
		label: renderRouterLink('category', '分类管理'),
		key: 'category',
		icon: renderIcon(GridIcon)
	},
	{
		label: renderRouterLink('tag', '标签管理'),
		key: 'tag',
		icon: renderIcon(PricetagIcon)
	},
	{
		label: renderRouterLink('comment', '评论管理'),
		key: 'comment',
		icon: renderIcon(ChatboxIcon)
	}
]

const options = [
	{
		label: '退出登录',
		key: 'logout'
	}]

function handleSelector(key: string) {
	switch (key) {
		case 'logout':
			logout()
			break
	}
}

function logout() {
	// TODO 退出登录
	msg.success('退出登录')
}

const collapsed = ref(true)

onMounted(() => {
	console.log(route.matched)
})
</script>

<template>
	<div class="home-container h-screen" >
		<n-layout has-sider class="h-full">
			<n-layout-sider
					bordered
					collapse-mode="width"
					:collapsed="!collapsed"
					:collapsed-width="64"
					:width="200"
					:native-scrollbar="false"
					:inverted="false">
				<p class="p-4 text-center font-bold">Blog后台管理系统</p>
				<n-menu
						:value="route.name"
						:inverted="false"
						:collapsed-width="64"
						:collapsed-icon-size="22"
						:options="menuOptions"
						:collapsed="!collapsed"
				/>
			</n-layout-sider>
				<n-layout >
						<n-layout-header>
							<div class="h-16 px-10 flex items-center border-b text-right">
							<toggle-switch v-model="collapsed"></toggle-switch>
							<div class="ml-auto">
								<n-dropdown trigger="hover" :show-arrow="true" :options="options" @select="handleSelector">
									<n-avatar
											round
											:size="38"
											src="/md.jpg"
									/>
								</n-dropdown>
							</div>
							</div>
						</n-layout-header>
					<n-layout-content style="background-color: #f9f9f9">
						<router-view></router-view>
					</n-layout-content>
				</n-layout>
		</n-layout>
	</div>
</template>

<style scoped lang="less">
:deep(.n-layout-scroll-container) {
	display: flex;
	flex-direction: column;
}
</style>