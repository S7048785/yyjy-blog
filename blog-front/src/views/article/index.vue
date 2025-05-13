<script setup lang="ts">
import {TimeOutline, EyeOutline, ChatbubbleOutline, HeartOutline} from '@vicons/ionicons5'
import {emitter} from "@/utils/emitter.ts";
import type {ArticleCard} from "@/interface/res/Article.ts";
import request from "@/utils/request.ts"
import {formatRelativeTime} from "@/utils/day.js.ts";
const router = useRouter()
const loading = ref(true)

const data = ref<ArticleCard[]>([])

const page = reactive({
  current: 1,
  size: 10,
})

const hasMore = ref(true);

const getArticleList = async () => {
  loading.value = true

  try {
    const res: any = await request.get('/article/list', {
      params: {
        current: page.current,
        size: page.size,
      }
    })
    page.current++
    data.value.push(...res.records)

    if (res.records.length === data.value.length) {
      hasMore.value = false
      return
    }
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  await getArticleList()
})
</script>

<template>
  <div class="article-container">
    <n-space vertical v-if="!data.length" style="width: 800px; margin-inline: auto; margin-top: 20px; padding-inline: 24px">
      <n-skeleton text style="width: 40%" />
      <n-skeleton text/>
      <n-skeleton text style="width: 80%" />
      <n-skeleton text style="width: 60%" />
    </n-space>
    <div v-else class="article-list">
      <div class="article-item" v-for="item in data" :key="item.id">
        <n-card :bordered="false" style="--n-color: none;">
          <template #default>
            <div class="content" @click="router.push('/article/' + item.id)">
              <div class="title" v-text="item.title"></div>
              <div class="description">
                <n-ellipsis v-text="item.summary" style="max-width: 240px" :line-clamp="2" :tooltip="false">
                </n-ellipsis>
              </div>
              <div class="meta" >
                <div class="icon-list" @click.stop>
                  <n-icon size="18"><EyeOutline/></n-icon> {{ item.viewCount}}
                  <n-icon size="18"><TimeOutline/></n-icon> {{ formatRelativeTime(item.createTime)}}
                  <n-icon size="18"><HeartOutline/></n-icon> {{item.likeCount}}
                  <n-icon size="18"><ChatbubbleOutline/></n-icon> {{ item.commentCount}}
                </div>
                <div class="tag">
                  <n-tag :bordered="false" v-for="tag in item.tags" :key="tag">{{tag}}</n-tag>
                </div>
              </div>
            </div>
            <a href="javascript: void(0)">
              <img :src="item.thumbnail" alt="">
            </a>
          </template>
        </n-card>
      </div>
    </div>
    <div class="footer" v-show="!loading">
      <n-button @click="getArticleList" :disabled="loading" v-if="hasMore">查看更多</n-button>
      <p v-else style="color: #666">没有更多了</p>
    </div>
  </div>
</template>

<style scoped lang="less">
.article-container {
  width: 100%;
  .article-list {
    display: flex;
    flex-direction: column;
    align-items: center;
    .article-item {
      padding-bottom: 20px;
      width: 800px;
      box-sizing: border-box;
      margin-bottom: 20px;
      border-radius: 12px;
      transition: .2s;
      box-shadow:0 0px 4px 0px rgba(0, 0, 0, 0.1);
      transform: translate3d(0px, -1.83785px, 0px);
      &:hover {
        box-shadow: 0 5px 20px -4px rgba(0,0,0,0.15);
        //box-shadow: 0 5px 15px 1px rgba(0,0,0,0.15);
        //transform: translate3d(0px, -0.136077px, 0px);
        //transform: translate3d(0, -2px, 0);
        transform: translate3d(0px, -5px, 0px);
        //box-shadow: 0 15px 20px -20px rgba(0, 0, 0, 0.5);
      }
      :deep(.n-card) {
        height: 120px;
        padding-block: 5px;

        .n-card__content {

          font-size: 16px;
          display: flex;
          justify-content: space-between;

          .content {

            width: 60%;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            cursor: pointer;
            flex: 1;
            padding-right: 30px;
            .title {
              width: fit-content;
              font-size: 18px;
              font-weight: bold;
              margin-bottom: 5px;
              &:hover {
                color: #008c8c;
              }
            }
            .description {
              //margin-bottom: 5px;
            }
            .meta {
              display: flex;
              justify-content: space-between;
              .icon-list {
                display: flex;
                align-items: center;
                color: #666;
                font-size: 14px;
                cursor: auto;

                .n-icon {
                  margin-inline: 8px 5px;

                  &:first-child {
                    margin-left: 0;
                  }
                }
              }
              .tag {
                .n-tag {
                  background-color: #f2f3f5;
                  color: #8a919f;
                  margin-right: 5px;
                  cursor: pointer;
                  &:hover {
                    color: #008c8c;
                  }
                }
              }
            }
          }
          a {
            width: 150px;
            height: 100px;
            overflow: hidden;
            border-radius: 15px;
            img {
              height: 100%;
              width: 100%;
              object-fit: cover;
              transition: .5s;

              &:hover {
                transform: scale(1.1);
              }
            }
          }
        }
      }
    }
  }
  .footer {
    display: flex;
    align-items: center;
    justify-content: center;
    .n-button {
      background-color: #02bdbd;
      color: #fff;
      padding: 8px 30px;
      border-radius: 8px;
      border: none;
      &:hover {
        background-color: #01d1d1;
      }
      &:active {
        background-color: rgb(35, 147, 147);
      }
    }
  }
}
</style>