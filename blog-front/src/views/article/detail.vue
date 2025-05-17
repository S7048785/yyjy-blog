<script setup lang="ts">
import {EyeOutline, TimeOutline} from "@vicons/ionicons5";
import CommentSection from "@/views/article/commentSection.vue";
import request from '@/utils/request'
import {useRoute} from 'vue-router'
import {useCommentStore} from "@/stores/comment.ts";
import type {Article} from "@/interface/res/Article.ts"
import {useMessage} from 'naive-ui'
import {MdPreview, MdCatalog} from 'md-editor-v3'
import {lineNumbers} from '@codemirror/view';
import {formatRelativeTime} from "@/utils/day.js.ts";
import {useWindowScroll} from '@vueuse/core'

const {y} = useWindowScroll()
const showButton = computed(() => y.value > 700)
// 返回顶部
const scrollToTop = () => {
  window.scrollTo({
    top: 0,
    behavior: 'smooth'
  })
}

const message = useMessage();

const commentStore = useCommentStore();
const route = useRoute()

const id = 'article-preview'; // 唯一ID，用于关联目录组件
const scrollElement = document.documentElement;

const show = ref(false);

const isLike = ref(false);

const likeColor = computed(() => {
  return isLike.value ? '#008c8c' : '#aaaaaa';
});

const likeActive = async () => {

  if (isLike.value) {
    message.info('You have already liked post', {duration: 2000});
    return;
  }
  isLike.value = true;
  message.success('Thanks for your like', {duration: 2000});
  // 发送点赞请求
  await request.post('/article/like/' + route.params.id)
}

const data = ref<Article>({} as any);

onMounted(async () => {
  const res: any = await request.get('/article/' + route.params.id)
  data.value = res.data;
  document.title = res.data.title;

  // 并行获取点赞状态和评论列表
  await Promise.all([
      // 获取点赞状态
    request.get('/article/liked-status/' + route.params.id).then(res => {
      isLike.value = res.data;
    }),
    // 获取评论列表
    commentStore.getCommentList(route.params.id as string),
    // 浏览量+1
    request.post('/article/view/' + route.params.id)])
})

onUnmounted(() => {
  commentStore.reset();
})
</script>

<template>
  <div class="detail-container" ref="el">
    <n-space vertical v-if="!data.content"
             style="width: 800px; margin-inline: auto; margin-top: 20px; padding-inline: 24px">
      <n-skeleton text style="width: 40%"/>
      <n-skeleton text/>
      <n-skeleton text style="width: 80%"/>
      <n-skeleton text style="width: 60%"/>
    </n-space>
    <div v-else class="header">
      <h1 class="header-title" v-text="data.title"></h1>
      <div class="header-meta">
        <div class="icon-list">
          <n-icon size="18">
            <TimeOutline/>
          </n-icon>
          <span v-text="formatRelativeTime(data.createTime)"></span>
          <n-icon size="18">
            <EyeOutline/>
          </n-icon>
          <span v-text="data.viewCount"></span>
        </div>
      </div>
    </div>

    <div class="content">
      <MdPreview
          :editorId="id"
          :modelValue="data.content"
          :editorConfig="{
            extensions: [lineNumbers()]
          }"
      />
    </div>
    <div class="footer">
      更新于 {{ data.updateTime }}

    </div>
    <n-drawer v-model:show="show" :block-scroll="false" :default-width="500">
      <n-drawer-content closable>
        <template #header>
          <h4 style="padding-block: 10px">评论 <span style="font-size: 16px" v-text="data.commentCount"></span></h4>
        </template>
        <template #default>
          <comment-side></comment-side>
        </template>
      </n-drawer-content>
    </n-drawer>

    <div class="article-panel" v-show="data.id">
      <n-flex vertical :size="20">
        <n-float-button @click="likeActive" position="relative" style="width: 50px; min-height: 50px">
          <n-badge :value="data.likeCount" :max="999" :offset="[6, -8]" :color="likeColor">
            <n-icon size="24">
              <svg id="Glyph" :fill="likeColor" viewBox="0 0 32 32" xml:space="preserve"
                   xmlns="http://www.w3.org/2000/svg"
              ><path d="M29.845,17.099l-2.489,8.725C26.989,27.105,25.804,28,24.473,28H11c-0.553,0-1-0.448-1-1V13  c0-0.215,0.069-0.425,0.198-0.597l5.392-7.24C16.188,4.414,17.05,4,17.974,4C19.643,4,21,5.357,21,7.026V12h5.002  c1.265,0,2.427,0.579,3.188,1.589C29.954,14.601,30.192,15.88,29.845,17.099z" id="XMLID_254_"></path>
                <path
                    d="M7,12H3c-0.553,0-1,0.448-1,1v14c0,0.552,0.447,1,1,1h4c0.553,0,1-0.448,1-1V13C8,12.448,7.553,12,7,12z   M5,25.5c-0.828,0-1.5-0.672-1.5-1.5c0-0.828,0.672-1.5,1.5-1.5c0.828,0,1.5,0.672,1.5,1.5C6.5,24.828,5.828,25.5,5,25.5z"
                    id="XMLID_256_"></path></svg>
            </n-icon>
          </n-badge>
        </n-float-button>
        <n-float-button @click="show = true" position="relative" style="width: 50px; min-height: 50px">
          <n-badge :value="data.commentCount" :max="999" :offset="[6, -8]" color="#aaa">
            <n-icon size="20">
              <svg fill="#aaa" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" width="200" height="200">
                <path
                    d="M512 0c282.763636 0 512 229.236364 512 512 0 141.405091-72.797091 258.606545-165.469091 351.278545l55.482182 89.693091c19.176727 30.999273-2.792727 71.028364-39.237818 71.028364H512C229.236364 1024 0 794.763636 0 512S229.236364 0 512 0zM254.138182 465.454545a65.163636 65.163636 0 1 0 0 130.327273 65.163636 65.163636 0 0 0 0-130.327273z m516.189091-9.30909a65.163636 65.163636 0 1 0 0 130.327272 65.163636 65.163636 0 0 0 0-130.327272z m-253.44 0a65.163636 65.163636 0 1 0 0 130.327272 65.163636 65.163636 0 0 0 0-130.327272z"
                    p-id="7152"></path>
              </svg>
            </n-icon>
          </n-badge>
        </n-float-button>
        <n-float-button @click="scrollToTop" v-show="showButton" position="relative"
                        style="width: 50px; min-height: 50px">
          <n-badge :max="999" :offset="[6, -8]" color="#aaa">
            <n-icon size="28">
              <svg fill="#aaa" t="1747215709061" class="icon" viewBox="0 0 1024 1024" version="1.1"
                   xmlns="http://www.w3.org/2000/svg" p-id="23963" width="200" height="200">
                <path
                    d="M128 128h768v85.333333H128V128z m384 110.336l-30.165333 30.165333-170.666667 170.666667-30.165333 30.165333L341.333333 529.664l30.165334-30.165333L469.333333 401.664V896h85.333334V401.664l97.834666 97.834667 30.165334 30.165333L742.997333 469.333333l-30.165333-30.165333-170.666667-170.666667L512 238.336z"
                    p-id="23964"></path>
              </svg>
              <!--              <ChatbubbleOutline/>-->
            </n-icon>
          </n-badge>
        </n-float-button>
      </n-flex>
    </div>
  </div>

  <div class="article-navigation">
    <h3>目 录</h3>
    <MdCatalog :editorId="id" :scrollElement="scrollElement"/>
  </div>

</template>

<style scoped>

.detail-container {
  width: 100%;
  padding: 20px 80px;
  @media (max-width: 768px) {
    padding: 20px;
  }

  .header {
    margin-bottom: 30px;

    .header-title {
      padding-block: 10px;
    }

    .header-meta {
      .icon-list {
        display: flex;
        align-items: center;
        color: #666;
        font-size: 14px;
        cursor: auto;

        span {
          margin-right: 10px;
        }

        .n-icon {
          margin-right: 5px;

          &:first-child {
            margin-left: 0;
          }
        }
      }
    }
  }

  .content {
    font-size: 16px;
    color: #4d4d4d;
    width: calc(100%);

    :deep(code) {
      font-family: emoji;
    }
  }

  .footer {
    float: right;
    margin-top: 50px;
    color: #aaa;
  }

  .article-panel {
    position: fixed;
    top: 220px;
    margin-left: -9rem;
    @media (max-width: 1140px) {
      margin-left: -4rem;
    }
    @media (max-width: 768px) {
      display: none;
    }

  }
}

:deep(.n-drawer-body) {
  .n-drawer-body-content-wrapper {
    &::-webkit-scrollbar {
      display: none; /* 隐藏滚动条 */
    }
  }

}


.article-navigation {
  top: 15px;
  right: 40px;
  position: fixed;
  width: 200px;
  padding: 20px;
  min-height: 150px;
  background-color: #fff;
  @media (max-width: 1500px) {
    display: none;
  }

  h3 {
    margin-left: auto;
    margin-right: auto;
    text-align: center;
    margin-bottom: 10px;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
    width: 80%;
    word-spacing: 10px;
  }

  /*:deep(.md-editor-catalog-active) {
    span {
      color: #18c28d
    }
  }*/

  :deep(.md-editor-catalog-indicator) {
    background-color: #18c28d;
  }

  :deep(.md-editor-catalog-link) {
    span {
      &:hover {
        color: #18c28d
      }

      margin-left: 10px;
    }
  }
}
</style>