<script setup lang="ts">
import { RouterView } from 'vue-router'

</script>

<template>

  <div style="background-color: #fafafa">
      <n-message-provider >
        <RouterView />
      </n-message-provider>
  </div>

</template>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

</style>
