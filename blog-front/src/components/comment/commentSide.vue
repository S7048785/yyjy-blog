<script setup lang="ts">
const route = useRoute()
</script>

<template>
  <div class="comment-container">
    <comment-area :params="{
      articleId: route.params.id,
    }"></comment-area>
  <comment-list></comment-list>
  </div>

</template>

<style scoped>
.comment-container {
  width: 100%;

}
</style>