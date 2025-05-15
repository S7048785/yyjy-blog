<script setup lang="ts">
import {useDebounceFn} from "@vueuse/core";
import {useCommentStore} from "@/stores/comment.ts";
import {formatRelativeTime} from "@/utils/day.js";

const commentStore = useCommentStore();
const route = useRoute()

const submitShow = ref(false)


const textFocus = () => {

  if (form.content !== '') {
    submitShow.value = true
    return
  }
  submitShow.value = false
}

const form = reactive({
  name: '',
  content: ''
})

const submit = () => {
  if (form.name === '' || form.content === '') {
    return;
  }
  console.log(1)
}

const debounceFn = useDebounceFn( async () => {
  if (!commentStore.hasMore) {
    return
  }
  // TODO 发送请求
  await commentStore.getCommentList(route.params.id as string);

}, 500);

onMounted(async () => {

})
</script>

<template>
  <div class="comment-container">
    <div class="area" v-click-outside="textFocus" >
      <div class="name">
        <input class="input" v-model="form.name" placeholder="昵称" required type="text">
        <span class="input-border"></span>
      </div>
      <div class="edit">
        <textarea @focus="submitShow = true" v-model="form.content" maxlength="100" rows="4"
                  placeholder="快来评论吧" name="">
      </textarea>
      </div>
      <div class="footer" v-show="submitShow">
        <n-button size="small" strong secondary round @click="submit" type="primary">
          提交
        </n-button>
      </div>
    </div>
    <div class="comment-list" v-show="commentStore.commentList">
      <div class="comment-item" v-for="(item, index) in commentStore.commentList" :key="index">
        <a class="avatar">
          <img style="width: 40px; height: 40px;" :src="`/src/assets/img/avatar${!item.isAuthor ? index % 5 + 1 : ''}.jpg`" alt="">
        </a>
        <div class="content">
          <div class="name">
            <span v-text="item.nickName"></span>
          </div>
          <div v-text="item.content" class="text">
          </div>
          <div class="meta">
            <span class="item" v-text="item.ipAddress"></span>
            <span class="item" v-text="formatRelativeTime(item.createTime)"></span>
          </div>
        </div>
      </div>
      <div class="comment-footer" style="text-align: center">
        <div v-if="commentStore.hasMore" v-lazy="debounceFn" >{{ '加载中...'}}</div>
        <div v-else> {{'没有更多了'}}</div>
        <div v-show="!commentStore.commentList">
          <n-empty description="暂无评论" />
        </div>
      </div>
    </div>
  </div>

</template>

<style scoped>
.comment-container {
  width: 100%;

  .area {
    padding: 10px 20px;
    background: rgb(247, 249, 251);
    border-radius: 15px;
    margin-bottom: 50px;

    .name {
      margin-bottom: 10px;
      position: relative;
      --width-of-input: 200px;
      --border-height: 1px;
      --border-before-color: rgba(221, 221, 221, 0.39);
      --border-after-color: #02bdbd;
      --input-hovered-color: #4985e01f;

      .input {
        font-size: 0.9rem;
        background-color: transparent;
        width: 100%;
        box-sizing: border-box;
        padding-inline: 0.5em;
        padding-block: 0.7em;
        border: none;
        border-bottom: var(--border-height) solid var(--border-before-color);

        &:focus {
          outline: none;
        }
        &:focus ~ .input-border {
          width: 100%;
        }
      }

      .input-border {
        position: absolute;
        background-image: linear-gradient(to right, var(--border-after-color), #008c8c);
        width: 0;
        height: 2px;
        bottom: 0;
        left: 0;
        transition: 0.3s;
      }
    }
    .footer {
      display: flex;
      justify-content: flex-end;
    }

    input, textarea {
      border: none;
      font-size: 1em;
      background: transparent;

      &:focus {
        outline: none;
      }
    }

    input {
      width: 100%;
      padding-bottom: 10px;

      &:focus {
        border-bottom: 1px solid #008c8c;
      }

    }

    textarea {
      width: 100%;
      resize: none;

      &::-webkit-scrollbar {
        width: 2px;
        background-color: transparent;
      }

      &::-webkit-scrollbar-thumb {
        -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
        background-color: #00ffff;
      }
    }
  }

  .comment-list {
    .comment-item {
      padding-left: 50px;
      font-size: 16px;
      position: relative;
      border-bottom: 1px solid #eee;
      margin-bottom: 20px;
      padding-bottom: 10px;
      .avatar {
        position: absolute;
        left: 0;

        img {
          border-radius: 50%;
          object-fit: cover;
        }
      }

      .text {
        padding-block: 5px;
      }

      .meta {
        font-size: 14px;
        color: #999;

        .item {
          margin-right: 10px;
        }
      }
    }
  }
}
</style>