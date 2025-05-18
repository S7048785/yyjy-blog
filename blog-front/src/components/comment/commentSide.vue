<script setup lang="ts">

import {useCommentStore} from "@/stores/comment.ts";
import type {Comment} from "@/interface/res/Comment.ts";

const route = useRoute()
const comment = useCommentStore()
const addComment = (data: Comment) => {
  comment.commentList.unshift(data)
}
</script>

<template>
  <div class="comment-container">
    <comment-area @publish="addComment" :params="{
      articleId: route.params.id as string,
    }"></comment-area>
  <comment-list></comment-list>
  </div>

</template>

<style scoped>
.comment-container {
  width: 100%;

}
</style>