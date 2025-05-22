
export const useRouterStore = defineStore('routerh', () => {

	const historyList = reactive<any[]>([])

	const addHistory = (route: { name: string, title: string}) => {
		historyList.push(
				route
		)
	}

	const spliceHistory = (index: number) => {
		historyList.splice(index)
	}

	const hasHistory = (name: string) => {
		return historyList.some(item => item.name === name)
	}

	return {
		 historyList,
		 addHistory,
		spliceHistory,
		hasHistory
	}
})