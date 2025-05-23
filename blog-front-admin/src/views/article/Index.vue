<script setup lang="ts">
import {NTag, NButton, zhCN, dateZhCN, type DataTableColumns} from "naive-ui"
import {getArticleList, deleteArticle, deleteArticleBatch} from "@/api/article.ts";
import {getCategoryList} from "@/api/category.ts";
import request from "@/utils/request.ts";
import type {ArticleCol} from "@/interface/request/article.ts";
import type {ArticleColumn} from "@/interface/response/article.ts";

// 新增和编辑数据的表单弹窗
const showModal = ref(false);
// 加载状态
const loading = ref(false);
// 消息
const msg = useMessage();
// 弹窗
const dialog = useDialog()
const formRef = ref(null);
// 分类数据
const categoryOptions = ref<any[]>([
	// {
	// 	label: '技术',
	// 	value: '0',
	// },
	// {
	// 	label: '生活',
	// 	value: '1',
	// },
	// {
	// 	label: '日志',
	// 	value: '3',
	// },
	// {
	// 	label: '项目',
	// 	value: '4',
	// },
	// {
	// 	label: '其他',
	// 	value: '5',
	// }
])
// 选择分类时触发回调
const searchCategory = async () => {
  if (categoryOptions.value.length > 0) {
    return;
  }
  loading.value = true;
  (await getCategoryList()).data.forEach((item: any) => {
    categoryOptions.value.push({
      label: item.name,
      value: item.id
    })
  })
  loading.value = false
}
// 状态数据
const statusOptions = ref([{label: '已发布', value: '0'}, {label: '草稿', value: '1'}])

// 查询表单数据
const queryFormValue = reactive({
	id: null,
	title: null,
	category: null,
	tags: [],
  date: [1746028800000, Date.now()],
	status: null
} as any);
window.queryFormValue = queryFormValue
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
  tags: {
    trigger: ['change'],
    validator(rule: unknown, value: string[]) {
      if (value.length > 3)
        return new Error('不得超过四个标签')
      return true
    }
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
				return row.tags?.split(',').map((tagKey: any) => {
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
							class: 'border border-solid rounded-md bg-transparent',
							onClick: () => actions.editRow(row)
						},
						{default: () => '编辑'}
				), h(
						NButton,
						{
							strong: true,
							tertiary: true,
							size: 'small',
							color: 'red',
							class: 'border border-solid rounded-md bg-transparent',
							onClick: () => actions.deleteRow(row)
						},
						{default: () => '删除'}
				)]
			}
		}
	] as any,
	data: [],
})

// 分页数据
const pagination = reactive({
	page: 1,
	pageCount: 1,
	pageSize: 10,
	showSizePicker: true,
	pageSizes: [10, 20],
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


// 表格行键 以id作为键
const rowKey = (row: ArticleColumn) => row.id;

// 选中的行数据 存储选中的文章的id
const checkedRowKeysRef = ref<number[]>([]);

// 监听选中行数据
const handleCheck = (rowKeys: any) => {
	checkedRowKeysRef.value = rowKeys
}


// 操作元素
const actions = {
  queryData: async () => {
    //TODO 搜索
    loading.value = true
    const res = await getArticleList(
    {
      id: queryFormValue.id,
      title: queryFormValue.title,
      category: queryFormValue.category,
      tags: queryFormValue.tags,
      date: queryFormValue.date,
      status: queryFormValue.status
    })
    tableData.data = res.data
    loading.value = false
  },
  resetForm: () => {
    queryFormValue.id = null
    queryFormValue.title = null
    queryFormValue.category = null
    queryFormValue.tags = []
    queryFormValue.date = [new Date(1746028800000), new Date()]
    queryFormValue.status = null
  },
	addRow: () => {
		// TODO 跳转添加页面
    showModal.value = true
	},
	editRow: (row: any) => {
		// TODO 跳转编辑页面
	},
	deleteRow: async (row: ArticleColumn) => {
    dialog.warning({
      title: '警告',
      content: `确定要删除 "${row.title}" 吗？`,
      positiveText: '确定',
      negativeText: '取消',
      onPositiveClick: async () => {
        if (await deleteArticle(row.id)) {
          msg.success('删除成功');
          await actions.queryData()
        }
      }
    })
    // await deleteArticle(row.id);
    // msg.success('删除成功');
	},
	deleteRows: async () => {
		// TODO 批量删除
    dialog.warning({
      title: '警告',
      content: `确定要删除选中的 ${checkedRowKeysRef.value.length} 条数据吗？`,
      positiveText: '确定',
      negativeText: '取消',
      onPositiveClick: async () => {
        if (await deleteArticleBatch(checkedRowKeysRef.value)) {
          msg.success('删除成功');
          await actions.queryData()
        }
      }
    })
	}
}

function rowClassName(row: any) {
	return 'center'
}

onBeforeMount(async () => {
  loading.value = true
  const res: any = await request.get('/article/list')
  tableData.data = res.data
	// TODO 请求分类数据

  loading.value = false
})
</script>

<template>
	<div class="md:p-4 h-full">
		<div>
			<n-card title="搜索" class="md:mb-4" size="small">
				<n-form
						ref="formRef"
						:model="queryFormValue"
						:rules="rules"
						label-placement="left"
						:show-require-mark="false"
						class="flex flex-wrap"
				>
					<n-grid cols="1 380:2 824:4" class="[&>*]:pr-8">
						<n-grid-item>
							<n-form-item label-width="auto" label="ID" path="id">
								<n-input class="text-sm" v-model:value="queryFormValue.id" placeholder="请输入ID"/>
							</n-form-item>
						</n-grid-item>
						<n-grid-item>
							<n-form-item label-width="auto" label="标题" path="title">
								<n-input v-model:value="queryFormValue.title" placeholder="请输入标题"/>
							</n-form-item>
						</n-grid-item>
            <n-grid-item>
              <n-form-item label-width="auto" label="状态" path="status">
                <n-select v-model:value="queryFormValue.status" :options="statusOptions"
                          placeholder="请选择状态"></n-select>
              </n-form-item>
            </n-grid-item>
            <n-grid-item>
              <n-form-item label-width="auto" label="分类" path="category">
                <n-select v-model:value="queryFormValue.category"
                          :options="categoryOptions"
                          placeholder="请选择分类"
                          :loading="loading"
                          @focus="searchCategory"></n-select>
              </n-form-item>
            </n-grid-item>
            <n-grid-item>
              <n-form-item label-width="auto" label="日期" path="date">
                <n-config-provider :locale="zhCN" :date-locale="dateZhCN">
                  <n-date-picker @confirm="() => {
                    console.log(queryFormValue.date)
                  }" v-model:value="queryFormValue.date" type="daterange" clearable/>
                </n-config-provider>
              </n-form-item>
            </n-grid-item>
            <n-grid-item>
            <n-form-item label-width="auto" label="标签" path="tags">
              <n-dynamic-tags v-model:value="queryFormValue.tags" />
            </n-form-item>
          </n-grid-item>
						<n-grid-item span="2">
							<n-form-item class="flex justify-end">
								<div>
									<n-button @click="actions.resetForm" class="mr-4 px-8 rounded-xl">重置</n-button>
									<n-button @click="actions.queryData" class="px-8 rounded-xl">搜索</n-button>
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
						<n-button @click="actions.addRow" ghost type="success" class="md:px-8 sm:px-4 mr-4 rounded-xl">新增</n-button>
						<n-button @click="actions.deleteRows" ghost type="error" class="sm:px-4 md:px-8 rounded-xl">删除</n-button>
					</div>
				</template>
				<n-config-provider>
					<n-data-table
              :loading="loading"
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
    <n-modal v-model:show="showModal">
      <n-card
          style="width: 600px"
          title="模态框"
          :bordered="false"
          size="huge"
          role="dialog"
          aria-modal="true"
      >
        <template #header-extra>
          噢！
        </template>
        内容
        <template #footer>
          尾部
        </template>
      </n-card>
    </n-modal>
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