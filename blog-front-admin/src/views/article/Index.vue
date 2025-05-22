<script setup lang="ts">
import {NTag, NButton} from "naive-ui"
import {zhCN, dateZhCN} from 'naive-ui'; // 导入中文语言包


const msg = useMessage();
const formRef = ref(null);
const categoryOptions = ref([
	{
		label: '技术',
		value: '0',
	},
	{
		label: '生活',
		value: '1',
	},
	{
		label: '日志',
		value: '3',
	},
	{
		label: '项目',
		value: '4',
	},
	{
		label: '其他',
		value: '5',
	}
])
const statusOptions = ref([{label: '已发布', value: '0'}, {label: '草稿', value: '1'}])

// 查询表单数据
const formValue = reactive({
	id: null,
	title: null,
	category: null,
	tags: [],
	date: [1746028800000, Date.now()],
	status: null
});

const resetForm = () => {
	formValue.id = null
	formValue.title = null
	formValue.category = null
	formValue.tags = []
	formValue.date = [1746028800000, Date.now()]
	formValue.status = null
}
const queryData = () => {
	//TODO 搜索
}

// 表单校验规则
const rules = {
	id: {
		required: false,
		message: '请输入文章ID',
		trigger: ['input']
	},
	title: {
		required: false,
		message: '请输入文章标题',
		trigger: ['input']
	},
	category: {
		required: false,
		message: '请输入文章分类',
		trigger: ['input']
	},
	tags: {
		required: false,
		message: '请输入文章标签',
		trigger: ['input']
	},
	date: {
		required: true,
		message: '请输入文章日期',
		trigger: ['input']
	},
	status: {
		required: false,
		message: '请输入文章状态',
		trigger: ['input']
	}
}

// 表格数据
const tableData = reactive({
	columns: [
		{
			type: 'selection',
		},
		{title: 'ID', key: 'id'},
		{title: '标题', key: 'title'},
		{title: '分类', key: 'category'},
		{
			title: '标签', key: 'tags', render(row: any) {
				return row.tags.map((tagKey: any) => {
					return h(
							NTag,
							{
								style: {
									marginRight: '6px'
								},
								type: 'info',
								bordered: false
							},
							{
								default: () => tagKey
							}
					)
				})
			}
		},
		{title: '日期', key: 'date'},
		{title: '状态', key: 'status'},
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
	data: null,
})

// 分页数据
const pagination = reactive({
	page: 1,
	pageCount: 1,
	pageSize: 5,
	showSizePicker: true,
	pageSizes: [5, 10, 20],
	onChange: (page: number) => {
		pagination.page = page;
	},
	onUpdatePageSize: (pageSize: number) => {
		pagination.pageSize = pageSize;
		pagination.page = 1;
	},
	prefix({itemCount}: { itemCount: any }) {
		return `Total is ${itemCount}.`
	}
})

//  表格行键
const rowKey = (row: any) => row.id;

// 选中的行数据
const checkedRowKeysRef = ref([]);

// 监听选中行数据
const handleCheck = (rowKeys: []) => {
	checkedRowKeysRef.value = rowKeys
}

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

function rowClassName(row: any) {
	return 'center'
}

onBeforeMount(() => {
	// TODO 请求表数据
	tableData.data = Array.from({length: 10}).fill(null).map((_, index) => {
		return {
			id: index,
			title: '文章标题',
			category: '生活',
			tags: ['标签1', '标签2'],
			date: '2021-01-01',
			status: '草稿'
		}
	}) as any

	// TODO 请求分类数据
})
</script>

<template>
	<div class="md:p-4 h-full">
		<div>
			<n-card title="搜索" class="md:mb-4" size="small">
				<n-form
						ref="formRef"
						:model="formValue"
						:rules="rules"
						label-placement="left"
						:show-require-mark="false"
						class="flex flex-wrap"
				>
					<n-grid cols="1 380:2 824:4" class="[&>*]:pr-8">
						<n-grid-item>
							<n-form-item label-width="auto" label="ID" path="id">
								<n-input class="text-sm" v-model:value="formValue.id" placeholder="请输入ID"/>
							</n-form-item>
						</n-grid-item>
						<n-grid-item>
							<n-form-item label-width="auto" label="标题" path="title">
								<n-input v-model:value="formValue.title" placeholder="请输入标题"/>
							</n-form-item>
						</n-grid-item>
						<n-grid-item>
							<n-form-item label-width="auto" label="标签" path="tags">
								<n-input v-model:value="formValue.tags" placeholder="请输入标签"/>
							</n-form-item>
						</n-grid-item>
						<n-grid-item>
							<n-form-item label-width="auto" label="日期" path="date">
								<n-config-provider :locale="zhCN" :date-locale="dateZhCN">
									<n-date-picker v-model:value="formValue.date" type="daterange" clearable/>
								</n-config-provider>
							</n-form-item>
						</n-grid-item>
						<n-grid-item>
							<n-form-item label-width="auto" label="状态" path="status">
								<n-select v-model:value="formValue.status" :options="statusOptions"
													placeholder="请选择状态"></n-select>
							</n-form-item>
						</n-grid-item>
						<n-grid-item>
							<n-form-item label-width="auto" label="分类" path="category">
								<n-select v-model:value="formValue.category" :options="categoryOptions"
													placeholder="请选择分类"></n-select>
							</n-form-item>
						</n-grid-item>
						<n-grid-item span="2">
							<n-form-item class="flex justify-end">
								<div>
									<n-button @click="resetForm" class="mr-4 px-8 rounded-xl">重置</n-button>
									<n-button @click="queryData" class="px-8 rounded-xl">搜索</n-button>
								</div>
							</n-form-item>
						</n-grid-item>
					</n-grid>
				</n-form>
			</n-card>
		</div>
		<div>
			<n-card title="文章列表" size="small" class="h-1/2">
				<template #header-extra>
					<div>
						<n-button ghost class="md:px-8 sm:px-4 mr-4 rounded-xl">刷新</n-button>
						<n-button @click="actions.addRow" ghost type="success" class="md:px-8 sm:px-4 mr-4 rounded-xl">新增</n-button>
						<n-button @click="actions.deleteRows" ghost type="error" class="sm:px-4 md:px-8 rounded-xl">删除</n-button>
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
	</div>
</template>

<style scoped lang="less">

:deep(.center) {
	td {
		text-align: center !important;
		button {
			margin-right: 10px;
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