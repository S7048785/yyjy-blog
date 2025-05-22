<script setup lang="ts">
const data = defineModel()
</script>

<template>
	<!-- From Uiverse.io by vinodjangid07 -->
	<div style="width: fit-content">
		<input v-model="data" id="checkbox" type="checkbox">
		<label class="toggle" for="checkbox">
			<div id="bar1" class="bars"></div>
			<div id="bar2" class="bars"></div>
			<div id="bar3" class="bars"></div>
		</label>
	</div>
</template>

<style scoped>
/* From Uiverse.io by vinodjangid07 */
#checkbox {
	display: none;
}

.toggle {
	position: relative;
	width: 30px;
	height: 30px;
	cursor: pointer;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	gap: 3px;
	transition-duration: .3s;
}

.bars {
	width: 80%;
	height: 3px;
	border: 1px solid;
	/*background-color: rgb(0, 0, 0);*/
	border-radius: 5px;
	transition-duration: .3s;
}

/* #checkbox:checked + .toggle .bars {
  margin-left: 13px;
} */

#checkbox:checked + .toggle #bar2 {
	transform: translateY(9px) rotate(60deg);
	margin-left: 0;
	transform-origin: right;
	transition-duration: .3s;
	z-index: 2;
}

#checkbox:checked + .toggle #bar1 {
	transform: translateY(16px) rotate(-60deg);
	transition-duration: .3s;
	transform-origin: left;
	z-index: 1;
}

#checkbox:checked + .toggle {
	transform: rotate(-90deg);
}
 #checkbox:checked + .toggle #bar3 {
	 transform: translateY(4px);
  transition-duration: .3s;
  transform-origin:right;
}

</style>