<script setup lang="ts">
import request from "@/utils/request.ts"

const {params} = defineProps<{
  params: {
    articleId: string;
    replyNickName?: string;
    parentId?: string;
    rootParentId?: string;
  },
}>()

let placeholder = '快来评论吧'

if (params.replyNickName) {
  placeholder = `回复 @${params.replyNickName} :`
}

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

const submit = async () => {
  if (form.name === '' || form.content === '') {
    return;
  }
  console.log({
    articleId: params.articleId,
    nickName: form.name,
    replyNickName: params.replyNickName,
    content: form.content,
    parentId: params.parentId,
    rootParentId: params.rootParentId
  })
  return;
  await request.post(
      'comment',
      {
        articleId: params.articleId,
        nickName: form.name,
        replyNickName: params.replyNickName,
        content: form.content,
        parentId: params.parentId,
        rootParentId: params.rootParentId
      }
  )
  console.log(1)
}

onDeactivated(() => {
  // emit('toggleReply')
  console.log(1)
})
</script>

<template>
    <div class="area" v-click-outside="textFocus" :id="params.replyNickName ? 'commentFormNew' : 'commentForm'">
      <div class="name">
        <input class="input" v-model="form.name" placeholder="昵称" required type="text">
        <span class="input-border"></span>
      </div>
      <div class="edit">
        <textarea @focus="submitShow = true" v-model="form.content" maxlength="100" rows="4"
                  :placeholder="placeholder" name="">
        </textarea>
      </div>
      <div class="footer" v-show="submitShow">
        <n-button size="small" strong secondary round @click="submit" type="primary">
          提交
        </n-button>
      </div>
    </div>
</template>

<style scoped>
.area {
  padding: 10px 20px;
  background: rgb(247, 249, 251);
  border-radius: 15px;

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
    font-size: 14px;
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
</style>