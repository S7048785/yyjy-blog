<script setup lang="ts">

import {useDebounceFn} from "@vueuse/core";
import {useRoute} from "vue-router";
import {useCommentStore} from "@/stores/comment.ts";
import CommentItem from "@/components/comment/commentItem.vue";

const route = useRoute();
const commentStore = useCommentStore();

const debounceFn = useDebounceFn( async () => {
  if (!commentStore.hasMore) {
    return
  }
  // TODO 发送请求
  await commentStore.getCommentList(route.params.id as string);

}, 500);

</script>

<template>
  <div class="comment-list" v-show="commentStore.commentList">
    <comment-item :commentList="commentStore.commentList" :number="1"></comment-item>
    <div class="comment-footer" style="text-align: center">
      <div v-if="commentStore.hasMore" v-lazy="debounceFn" >{{ '加载中...'}}</div>
      <div v-else-if="commentStore.commentList.length !== 0"> {{'没有更多了'}}</div>
        <n-empty v-else description="暂无评论" />
    </div>
  </div>
</template>

<style scoped>
.comment-list {
  margin-top: 50px;
}
</style>