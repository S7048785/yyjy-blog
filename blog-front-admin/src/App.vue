<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
	<n-message-provider :max="1">
		<RouterView />
	</n-message-provider>
</template>

<style scoped>

</style>