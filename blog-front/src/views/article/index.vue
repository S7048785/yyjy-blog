<script setup lang="ts">
import {TimeOutline, EyeOutline} from '@vicons/ionicons5'
import type {ArticleCard} from "@/interface/res/Article.ts";
import request from "@/utils/request.ts"
import {formatRelativeTime} from "@/utils/day.js.ts";
import emitter from "@/utils/emitter.ts"
const router = useRouter()
const loading = ref(true)

const data = ref<ArticleCard[]>([] as any)

const page = reactive({
  current: 1,
  size: 5,
})

const hasMore = ref(true);

const getArticleList = async () => {
  loading.value = true

  try {
    const res: any = await request.get('/article/list', {
      params: {
        current: page.current,
        size: page.size,
        max: data.value[data.value.length - 1]?.createTime
      }
    })
    page.current++
    data.value.push(...res.records)

    if (res.total === data.value.length) {
      hasMore.value = false
      return
    }
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  await getArticleList()
  emitter.on('toggleLoading', () => loading.value = !loading.value)
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
        <div  class="article-list" v-else>

      <transition-group appear name="list" tag="div">
        <div class="article-item" v-for="item in data" :key="item.id">
          <div class="article-card"><n-card :bordered="false" style="--n-color: none;">
            <template #default>
              <div class="content" @click="router.push('/article/' + item.id)">
                <div class="title" v-text="item.title"></div>
                <div class="description">
                  <n-ellipsis v-text="item.summary" style="color: #666" :line-clamp="1" :tooltip="false">
                  </n-ellipsis>
                </div>
                <div class="meta" >
                  <div class="icon-list" @click.stop>
                    <n-icon size="18"><EyeOutline/></n-icon> {{ item.viewCount}}
                    <n-icon size="18"><TimeOutline/></n-icon> {{ formatRelativeTime(item.createTime)}}
                    <n-icon size="18" v-show="item.likeCount !== 0">
                      <svg t="1747226813056" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4171" width="200" height="200"><path d="M958.378667 475.093333l-0.853334-2.986666c-0.512-1.621333-1.024-2.986667-1.237333-4.010667-12.288-48.384-43.008-68.394667-66.56-76.714667a154.666667 154.666667 0 0 0-49.493333-8.106666h-0.853334c-29.184-0.170667-58.581333-0.170667-87.893333-0.170667l-64.085333 0.085333h-21.589334c2.304-13.994667 4.48-27.392 7.082667-40.789333 8.789333-44.885333 7.210667-84.096-5.12-119.808-7.594667-22.186667-16.085333-44.288-24.192-65.578667l-8.96-23.594666c-10.112-27.008-28.245333-46.08-53.76-56.832a141.056 141.056 0 0 0-124.586667 3.882666c-33.066667 17.322667-50.858667 46.848-49.877333 83.114667 0.298667 12.032 0.597333 24.32 0.810667 36.224 0.426667 20.181333 0.768 41.088 1.706666 61.696a16.426667 16.426667 0 0 1-2.218666 9.898667c-7.296 12.714667-14.805333 25.514667-22.016 38.101333-17.066667 29.781333-34.773333 60.714667-53.077334 90.026667C305.066667 441.6 249.173333 448 249.173333 448H135.978667c-39.765333 0-71.978667 32.213333-71.978667 72.021333v367.957334c0 39.808 32.213333 72.021333 71.978667 72.021333h247.338666-0.128 1.109334c52.821333 0 106.24-0.213333 159.189333-0.213333 33.792 0 67.498667 0.213333 100.693333 0.213333H738.133333c21.205333 0 46.293333-1.408 72.021334-9.813333 53.589333-17.493333 88.192-61.184 92.373333-116.778667a157.269333 157.269333 0 0 0-2.56-45.909333c16.896-26.410667 24.490667-54.314667 22.656-83.114667a118.101333 118.101333 0 0 0-4.394667-24.32c16.128-23.978667 23.808-49.664 23.125334-76.544a135.594667 135.594667 0 0 0-4.906667-29.610667c9.216-13.610667 15.914667-28.586667 19.712-44.629333l3.882667-5.12v-43.946667l-1.621334-5.12z m-710.4 412.928H136.021333v-368h112.042667v367.957334z m640-383.232l-1.877334 7.808a64.426667 64.426667 0 0 1-9.301333 21.12l-18.901333 27.861334 9.301333 32.426666c1.194667 4.096 1.92 8.405333 2.218667 12.714667 0.170667 11.52-3.328 22.186667-10.922667 33.493333l-18.773333 27.989334 9.472 32.298666a38.698667 38.698667 0 0 1 1.621333 8.917334c0.682667 13.184-2.986667 26.069333-11.605333 39.381333l-15.786667 25.002667 5.290667 25.173333h-8.704 8.704l0.768 3.413333c1.706667 8.021333 2.218667 16.213333 1.408 24.32l-0.085334 0.597334v0.597333c-1.024 13.013333-5.12 24.192-12.288 33.28a63.018667 63.018667 0 0 1-30.634666 20.48c-16.085333 5.333333-33.365333 6.229333-49.664 6.229333H644.266667c-16.512 0-33.109333-0.213333-49.92-0.213333l-50.773334-0.085333c-47.616 0-103.68 0.298667-159.402666 0.298666H320.298667l0.810666-387.712c30.378667-14.762667 54.869333-36.096 71.509334-62.378666l0.085333-0.213334 0.085333-0.170666c18.730667-30.037333 36.522667-61.013333 53.717334-91.008l0.682666-1.109334 0.128-0.085333 0.085334-0.128c4.48-7.893333 9.301333-16.213333 13.909333-24.192 2.56-4.48 5.205333-8.917333 7.68-13.312 9.002667-15.274667 13.098667-32.682667 11.946667-50.304-0.853333-18.901333-1.237333-38.4-1.536-57.173333v-1.706667c-0.298667-13.696-0.512-25.301333-0.810667-36.821333-0.213333-6.058667 1.408-8.661333 1.92-9.6a23.253333 23.253333 0 0 1 9.386667-7.68c10.666667-5.589333 21.333333-8.32 32.426666-8.32 9.856 0 20.394667 2.304 31.061334 6.741333 5.973333 2.474667 10.496 5.973333 14.08 15.658667 3.029333 7.936 6.144 16 9.130666 24.021333 9.002667 23.466667 16.384 43.093333 23.424 63.317333 7.978667 23.381333 8.874667 50.346667 2.474667 82.474667a1376.853333 1376.853333 0 0 0-7.594667 43.093333l-13.781333 83.712h106.197333c10.496 0 21.077333 0 31.786667-0.085333h32.298667c25.813333 0 56.32 0 87.296 0.298667l0.512-0.128 0.469333-0.085334h0.426667c8.789333 0 17.493333 1.493333 25.898666 4.181334 7.893333 2.901333 15.786667 8.32 20.394667 26.453333 0.512 2.048 1.621333 3.968 1.621333 5.546667v13.653333z" fill="#000000" opacity=".65" p-id="4172"></path></svg></n-icon>
                    {{item.likeCount || ''}}
                  </div>
                  <div class="tag">
                    <n-tag :bordered="false" v-for="tag in (item.tags?.split(','))" :key="tag">{{tag}}</n-tag>
                  </div>
                </div>
              </div>
              <a href="javascript: void(0)" v-show="item.thumbnail">
                <img :src="item.thumbnail" alt="">
              </a>
            </template>
          </n-card></div>
        </div>
      </transition-group>
        </div>

    <div class="footer" v-show="data.length">
      <n-button @click="getArticleList" :disabled="loading" v-if="hasMore">查看更多</n-button>
    </div>
  </div>
</template>

<style scoped lang="less">
.article-container {
  //width: 100%;
  .article-list {
    //display: flex;
    //flex-direction: column;
    //align-items: center;
    margin-inline: auto;
    width: 80%;
    .list-enter-from {
      opacity: 0;
      transform: translateY(70px);
    }

    .list-enter-active {
      transition: .5s;
    }

    .article-item {

      .article-card {
        padding-bottom: 20px;
        //width: 80%;
        box-sizing: border-box;
        margin-bottom: 20px;
        border-radius: 12px;
        transition: .2s;
        box-shadow:0 0px 4px 0px rgba(0, 0, 0, 0.1);
        transform: translate3d(0px, -1.83785px, 0px);
        &:hover {
          box-shadow: 0 5px 20px -4px rgba(0,0,0,0.15);
          transform: translate3d(0px, -5px, 0px);
        }
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
                  margin-inline: 10px 4px;

                  &:first-child {
                    margin-left: 0;
                  }
                }
              }
              .tag {
                @media(max-width: 768px) {
                  display: none;
                }
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
            border-radius: 15px;
            img {
              @media(max-width: 768px) {
                display: none;
              }
              width: 150px;
              height: 100px;
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