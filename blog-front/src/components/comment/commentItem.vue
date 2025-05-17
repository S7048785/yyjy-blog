<script setup lang="ts">
import {formatRelativeTime} from "@/utils/day.js.ts";
import type {Comment} from "@/interface/res/Comment.ts";
import emitter from "@/utils/emitter.ts";
import {useCommentStore} from "@/stores/comment.ts";
const commentStore = useCommentStore();
defineProps<{
  commentList: Comment[];
}>()

const route = useRoute();

const replyActive = (index: string) => {
  //
  // if (commentStore.replyIndex === index) {
  //   showReply.value = !showReply.value;
  // } else {
  //   showReply.value = true;
  // }

  commentStore.replyIndex = commentStore.replyIndex === index ? '' : index;
}

</script>

<template>
  <div class="comment-item" v-for="(item, index) in commentList" :key="index">
    <a class="avatar">
      <img style="width: 40px; height: 40px;" :src="`/src/assets/img/avatar${!item.isAuthor ? index % 5 + 1 : ''}.jpg`" alt="">
    </a>
    <div class="content">
      <div class="header">
        <span class="nickname" v-text="item.nickName"></span>
        <n-button @click="replyActive(item.id)" :class="{show: commentStore.replyIndex === item.id}" text class="reply" v-text="commentStore.replyIndex === item.id ? '收起' : '回复'"></n-button>
      </div>
      <div v-text="item.content" class="text">
      </div>
      <div class="meta">
        <span class="item" v-text="formatRelativeTime(item.createTime)"></span>
        <span class="item" v-text="item.ipAddress"></span>
      </div>
    </div>
      <comment-area class="reply" v-if="commentStore.replyIndex === item.id" :params="{
        articleId: route.params.id,
        replyNickName: item.nickName,
        parentId: item.id,
        rootParentId: item.rootParentId
      }"></comment-area>
    <div class="children" v-show="item.children">
      <comment-item :comment-list="item.children || []"></comment-item>
    </div>
  </div>
</template>

<style scoped>
.comment-item {
  padding-left: 50px;
  font-size: 16px;
  position: relative;
  padding-block: 15px;
  .content {
    margin-bottom: 10px;
    &:hover {
      .header {
        .reply {
          display: block;
        }
      }

    }
    .header {

      .reply {
        display: none;
        color: #aaa;
        float: right;
        font-size: 14px;
      }
      .show {
        display: block;
      }
    }
  }
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


</style>