<script setup lang="ts">
import {NButton} from "naive-ui";

const msg = useMessage()

const tableData = reactive({
	columns: [
		{
			type: 'selection',
		},
		{
			title: 'ID',
			key: 'id',
		},
		{
			title: '分类名称',
			key: 'name',
		},
		{
			title: '文章数量',
			key: 'count',
		},
		{
			title: '操作',
			key: 'actions',
			render(row: any) {
				return [h(
						NButton,
						{
							strong: true,
							tertiary: true,
							size: 'small',
							color: 'blue',
							ghost: true,
							class: 'border border-solid rounded-md',
							onClick: actions.editRow
						},
						{default: () => '编辑'}
				), h(
						NButton,
						{
							strong: true,
							tertiary: true,
							size: 'small',
							color: 'red',
							ghost: true,
							class: 'border border-solid rounded-md',
							onClick: actions.deleteRow
						},
						{default: () => '删除'}
				)]
			}
		}
	],
	data: null
})

function rowClassName(row: any) {
	return 'center'
}

//  表格行键
const rowKey = (row: any) => row.id;
// 选中的行数据
const checkedRowKeysRef = ref([]);

// 监听选中行数据
const handleCheck = (rowKeys: []) => {
	checkedRowKeysRef.value = rowKeys
}

const pagination = reactive({
	page: 1,
	pageCount: 1,
	pageSize: 5,
	showSizePicker: true,
	pageSizes: [5, 10, 20],
	onChange: (page: number ) => {
		pagination.page = page;
	},
	onUpdatePageSize: (pageSize: number) => {
		pagination.pageSize = pageSize;
		pagination.page = 1;
	},
	prefix({ itemCount }: { itemCount: any }) {
		return `Total is ${itemCount}.`
	}
})

// 操作元素
const actions = {
	refresh: () => {
		// TODO 刷新表格
	},
	addRow: () => {
		// TODO 跳转添加页面
	},
	editRow: (row: any) => {
		// TODO 跳转编辑页面
	},
	deleteRow: (row: any) => {

		// TODO 删除
	},
	deleteRows: () => {
		// TODO 批量删除
	}
}

onBeforeMount(() => {
	// TODO 获取分类列表数据
	tableData.data = Array.from({length: 5}).fill(null).map((_, index) => {
		return {
			id: index,
			name: '分类名称',
			count: 10
		}
	}) as any
})
</script>

<template>
	<div class="md:p-4 h-full">
			<n-card title="分类列表" class="h-full" size="small">
				<template #header-extra>
					<div>
						<n-button ghost class="md:px-8 sm:px-4 mr-4 rounded-xl ">刷新</n-button>
						<n-button ghost type="success" class="md:px-8 sm:px-4 mr-4 rounded-xl">新增</n-button>
						<n-button ghost type="error" class="sm:px-4 md:px-8  rounded-xl">删除</n-button>
					</div>
				</template>
				<n-config-provider>
					<n-data-table
							:columns="tableData.columns"
							:data="tableData.data"
							:bordered="false"
							:row-key="rowKey"
							:pagination="pagination"
							:row-class-name="rowClassName"
							@update:checked-row-keys="handleCheck"
					/>
				</n-config-provider>
			</n-card>
	</div>
</template>

<style scoped>
:deep(.center) {
	td {
		text-align: center !important;
		button {
			margin-right: 20px;
			&:last-child {
				margin-right: 0;
			}
		}
	}
}
:deep(.n-data-table ) {
	.n-data-table-th {
		text-align: center;
	}
}
</style>
