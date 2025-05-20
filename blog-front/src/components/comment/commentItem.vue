<script setup lang="ts">
import {formatRelativeTime} from "@/utils/day.js.ts";
import type {Comment} from "@/interface/res/Comment.ts";
import {useCommentStore} from "@/stores/comment.ts";
import emitter from "@/utils/emitter.ts";

const commentStore = useCommentStore();
const {commentList, number} = defineProps<{
  commentList: Comment[];
  number: number
}>();

const route = useRoute();

const replyActive = (id: string) => {
  commentStore.replyIndex = commentStore.replyIndex === id ? '' : id;
}

const addComment = (data: Comment) => {
  commentStore.replyIndex = '';
  emitter.emit('commentCountIncrement');
  if (data.rootParentId) {
    commentStore.commentList.forEach(item => {
      if (item.id === data.rootParentId) {
        item.children.unshift(data)
        return
      }
    })
  } else if (data.parentId) {
    commentStore.commentList.forEach(item => {
      if (item.id === data.parentId) {
        item.children.unshift(data)
        return
      }
    })
  }
}

</script>

<template>
  <div class="comment-item" v-for="(item, index) in commentList" :key="item.id">
    <a class="avatar">
      <img style="width: 40px; height: 40px;" :src="`/img/avatar${!item.isAuthor ? index % 5 + 1 : ''}.jpg`"
        alt="">
    </a>
    <div class="content">
      <div class="header">
        <span class="nickname" style="color: #666" v-text="item.nickName">
        </span>
        <span style="margin-left: 5px;" v-if="item.parentId !== null">
          回复 <span style="color: #008c8c;">@{{item.replyNickName}}</span>
        </span>
        <n-button @click="replyActive(item.id)" :class="{show: commentStore.replyIndex === item.id}" text class="reply"
          v-text="commentStore.replyIndex === item.id ? '收起' : '回复'"></n-button>
      </div>
      <div v-text="item.content" class="text">
      </div>
      <div class="meta">
        <span class="item" v-text="formatRelativeTime(item.createTime)"></span>
        <span class="item" v-text="item.ipAddress"></span>
      </div>
    </div>
    <comment-area class="reply" v-if="commentStore.replyIndex === item.id" @publish="addComment" :params="{
        articleId: route.params.id as string,
        replyNickName: item.nickName,
        parentId: number === 1  ? null : item.id,
        rootParentId: number === 1 ? item.id : item.rootParentId
      }"></comment-area>
    <div class="children" v-if="item.children">
      <comment-item :comment-list="item.children" :number="2"></comment-item>
    </div>
  </div>
</template>

<style scoped>
.comment-item {
  padding-left: 50px;
  font-size: 16px;
  position: relative;
  padding-block: 10px;
  .content {
    /*margin-bottom: 10px;*/
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
    font-size: 14px;
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