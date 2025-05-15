<script setup lang="ts">
import {type MenuOption, useMessage} from "naive-ui";
import { SearchOutline, LogoGithub} from "@vicons/ionicons5"
import {RouterLink, useRoute} from "vue-router";
import {emitter} from "@/utils/emitter.ts";
const message = useMessage()

const route = useRoute();
const menuOptions: MenuOption[] = [
  {
    label: () =>
        h(
            RouterLink,
            {
              to: {
                name: 'article'
              }
            },
            {default: () => 'Article'}
        ),
    key: 'article'
  },
  {
    label: () =>
        h(
            RouterLink,
            {
              to: {
                name: 'category'
              }
            },
            {default: () => 'Category'}
        ),
    key: 'category'
  },
  {
    label: () =>
        h(
            RouterLink,
            {
              to: {
                name: 'animation'
              }
            },
            {default: () => 'Animation'}
        ),
    key: 'animation'
  },
  {
    label: () =>
        h(
            RouterLink,
            {
              to: {
                name: 'about'
              }
            },
            {default: () => 'About'}
        ),
    key: 'about'
  },
]
const activeKey = ref<string>(route.name as string);

</script>

<template>
  <div class="home">
    <div class="header">
      <div class="header-left">
        <n-avatar
            @click="emitter.emit('toggleLoading')"
            round
            :size="48"
            src="/src/assets/img/avatar.jpg"
        />
      </div>
      <div class="header-center">
        <n-menu
            v-model:value="activeKey"
            :options="menuOptions"
            mode="horizontal"
        />
      </div>
      <div class="header-right">
        <n-button text color="#333" style="font-size: 24px" >
          <n-icon>
            <SearchOutline/>
          </n-icon>
        </n-button>
      </div>
    </div>
    <div class="content">
      <router-view/>
    </div>
    <div class="footer">
      <div>
        <a href="https://space.bilibili.com/310219346" target="_blank">
          <n-icon size="24">
            <svg xmlns="http://www.w3.org/2000/svg"  viewBox="0 0 48 48" width="48px" height="48px"><path fill="#1e88e5" d="M36.5,12h-7.086l3.793-3.793c0.391-0.391,0.391-1.023,0-1.414s-1.023-0.391-1.414,0L26.586,12 h-5.172l-5.207-5.207c-0.391-0.391-1.023-0.391-1.414,0s-0.391,1.023,0,1.414L18.586,12H12.5C9.467,12,7,14.467,7,17.5v15 c0,3.033,2.467,5.5,5.5,5.5h2c0,0.829,0.671,1.5,1.5,1.5s1.5-0.671,1.5-1.5h14c0,0.829,0.671,1.5,1.5,1.5s1.5-0.671,1.5-1.5h2 c3.033,0,5.5-2.467,5.5-5.5v-15C42,14.467,39.533,12,36.5,12z M39,32.5c0,1.378-1.122,2.5-2.5,2.5h-24c-1.378,0-2.5-1.122-2.5-2.5 v-15c0-1.378,1.122-2.5,2.5-2.5h24c1.378,0,2.5,1.122,2.5,2.5V32.5z"/><rect width="2.75" height="7.075" x="30.625" y="18.463" fill="#1e88e5" transform="rotate(-71.567 32.001 22)"/><rect width="7.075" height="2.75" x="14.463" y="20.625" fill="#1e88e5" transform="rotate(-18.432 17.998 21.997)"/><path fill="#1e88e5" d="M28.033,27.526c-0.189,0.593-0.644,0.896-1.326,0.896c-0.076-0.013-0.139-0.013-0.24-0.025 c-0.013,0-0.05-0.013-0.063,0c-0.341-0.05-0.745-0.177-1.061-0.467c-0.366-0.265-0.808-0.745-0.947-1.477 c0,0-0.29,1.174-0.896,1.49c-0.076,0.05-0.164,0.114-0.253,0.164l-0.038,0.025c-0.303,0.164-0.682,0.265-1.086,0.278 c-0.568-0.051-0.947-0.328-1.136-0.821l-0.063-0.164l-1.427,0.656l0.05,0.139c0.467,1.124,1.465,1.768,2.74,1.768 c0.922,0,1.667-0.303,2.209-0.909c0.556,0.606,1.288,0.909,2.209,0.909c1.856,0,2.55-1.288,2.765-1.843l0.051-0.126l-1.427-0.657 L28.033,27.526z"/></svg>
          </n-icon>
        </a>
        <a href="https://x.com/YYJY_i" target="_blank">
          <n-icon size="24">
          <svg xmlns="http://www.w3.org/2000/svg"  viewBox="0 0 50 50" width="50px" height="50px"><path d="M 11 4 C 7.134 4 4 7.134 4 11 L 4 39 C 4 42.866 7.134 46 11 46 L 39 46 C 42.866 46 46 42.866 46 39 L 46 11 C 46 7.134 42.866 4 39 4 L 11 4 z M 13.085938 13 L 21.023438 13 L 26.660156 21.009766 L 33.5 13 L 36 13 L 27.789062 22.613281 L 37.914062 37 L 29.978516 37 L 23.4375 27.707031 L 15.5 37 L 13 37 L 22.308594 26.103516 L 13.085938 13 z M 16.914062 15 L 31.021484 35 L 34.085938 35 L 19.978516 15 L 16.914062 15 z"/></svg>
          </n-icon>
        </a>
        <a href="https://github.com/S7048785" target="_blank">
          <n-icon size="24">
            <svg xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" width="100" height="100" viewBox="0 0 50 50">
              <path d="M17.791,46.836C18.502,46.53,19,45.823,19,45v-5.4c0-0.197,0.016-0.402,0.041-0.61C19.027,38.994,19.014,38.997,19,39 c0,0-3,0-3.6,0c-1.5,0-2.8-0.6-3.4-1.8c-0.7-1.3-1-3.5-2.8-4.7C8.9,32.3,9.1,32,9.7,32c0.6,0.1,1.9,0.9,2.7,2c0.9,1.1,1.8,2,3.4,2 c2.487,0,3.82-0.125,4.622-0.555C21.356,34.056,22.649,33,24,33v-0.025c-5.668-0.182-9.289-2.066-10.975-4.975 c-3.665,0.042-6.856,0.405-8.677,0.707c-0.058-0.327-0.108-0.656-0.151-0.987c1.797-0.296,4.843-0.647,8.345-0.714 c-0.112-0.276-0.209-0.559-0.291-0.849c-3.511-0.178-6.541-0.039-8.187,0.097c-0.02-0.332-0.047-0.663-0.051-0.999 c1.649-0.135,4.597-0.27,8.018-0.111c-0.079-0.5-0.13-1.011-0.13-1.543c0-1.7,0.6-3.5,1.7-5c-0.5-1.7-1.2-5.3,0.2-6.6 c2.7,0,4.6,1.3,5.5,2.1C21,13.4,22.9,13,25,13s4,0.4,5.6,1.1c0.9-0.8,2.8-2.1,5.5-2.1c1.5,1.4,0.7,5,0.2,6.6c1.1,1.5,1.7,3.2,1.6,5 c0,0.484-0.045,0.951-0.11,1.409c3.499-0.172,6.527-0.034,8.204,0.102c-0.002,0.337-0.033,0.666-0.051,0.999 c-1.671-0.138-4.775-0.28-8.359-0.089c-0.089,0.336-0.197,0.663-0.325,0.98c3.546,0.046,6.665,0.389,8.548,0.689 c-0.043,0.332-0.093,0.661-0.151,0.987c-1.912-0.306-5.171-0.664-8.879-0.682C35.112,30.873,31.557,32.75,26,32.969V33 c2.6,0,5,3.9,5,6.6V45c0,0.823,0.498,1.53,1.209,1.836C41.37,43.804,48,35.164,48,25C48,12.318,37.683,2,25,2S2,12.318,2,25 C2,35.164,8.63,43.804,17.791,46.836z"></path>
            </svg>
          </n-icon>
        </a>
      </div>
    </div>
  </div>
</template>

<style scoped lang="less">
.home {
  margin-left: auto;
  margin-right: auto;
  max-width: 1000px;
  min-height: 100vh;
  display: flex;
  background-color: #fff;
  //padding-inline: 50px;
  flex-direction: column;


  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    //background-color: beige;
    min-height: 80px;
    padding: 30px;

    .header-center {
      text-align: center;
      .n-menu {
        box-shadow: 0px 7px 10px 0px #eee;
        border-radius: 25px;
        width: fit-content;
        padding: 0 40px;
        overflow-x: auto;
        font-size: 16px;

      }
    }
  }

  .footer {
    border-top: 1px solid;
    height: 50px;
    display: flex;
    border-top: 1px solid #ccc;
    align-content: center;
    flex-wrap: wrap;
    a {
      margin-right: 10px;
      .n-icon {
        fill: #000;
      }
    }
  }

  .content {
    position: relative;
    flex: 1;
    //min-height: 60vh;
  }

}
</style>