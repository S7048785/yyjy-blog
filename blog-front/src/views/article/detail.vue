<script setup lang="ts">
import {ChatbubbleOutline, EyeOutline, HeartOutline, TimeOutline} from "@vicons/ionicons5";
import CommentSection from "@/views/article/commentSection.vue";

import {useRoute} from 'vue-router'
import {useCommentStore} from "@/stores/comment.ts";

const commentStore = useCommentStore();
const route = useRoute()
// 或仅预览模式（更轻量）

import { MdPreview, MdCatalog, config } from 'md-editor-v3'
const id = 'article-preview'; // 唯一ID，用于关联目录组件
const scrollElement = document.documentElement;

import { lineNumbers } from '@codemirror/view';
const markdownText = ref(`
引入logback的依赖（springboot项目中该依赖已传递）、配置文件logback.xml

在非springboot项目中引入

\`\`\`xml
<dependency>
\t<groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.4.11</version>
</dependency>
\`\`\`

在resources下新增logback.xml配置文件

\`\`\`xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- 控制台输出 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度  %logger{50}: 最长50个字符(超出.切割)  %msg：日志消息，%n是换行符 -->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- 日志输出级别 -->
    <root level="debug">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
\`\`\`

## 配置文件详解

配置文件名：logback.xml

该文件是对Logback日志框架输出的日志进行控制，可以来配置输出的格式、位置及日志开关等

+ 常用的两种输出日志的位置：控制台、系统文件

\`\`\`xml
<!-- 控制台输出 -->
<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">...</appender>

<!-- 系统文件输出 -->
<appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">...</appender>
\`\`\`

+ 开启日志（ALL），关闭日志（OFF）

\`\`\`xml
<root level="ALL">
\t<appender-ref ref="STDOUT"/>
    <appender-ref ref="FILE"/>
</root>
\`\`\`



\`\`\`yaml
logging:
  level:
    com:
      example:
        mapper: debug
        service: info
        controller: info
\`\`\`



## 日志级别

日志级别指的是日志信息的类型，日志都会分级别，常用的日志如下（级别由低到高）

| 日志级别 | 说明 |
| -------- | ---- |
| trace | 追踪，记录程序运行轨迹（很少使用） |
| dubug | 调试，记录程序调试过程中的信息 |
| info | 记录一般信息，描述程序运行的关键事件，如网络连接、IO操作 |
| warn | 警告信息，记录潜在有害的信息 |
| error | 错误信息 |

可以在配置文件中灵活的控制输出那些类型的日志

\`\`\`xml
<root level="info">
\t<appender-ref ref="STDOUT"/>
    <appender-ref ref="FILE"/>
</root>
\`\`\`



定义日志记录对象Logger，记录日志

\`\`\`java
// LogTest.java
public class LogTest {
    \t\t\t\t\t\t\t\t\t\t\t\t\t// LogTest为当前类名
    private static final Logger log = LoggerFactory.getLogger(LogTest.class);

    @DeleteMapping("/")
    public Result delete(Integer id) {
        log.info("根据ID删除数据：{}", id);
    }
}
\`\`\`

或者在类前加上@Slf4j就可以直接使用log对象

\`\`\`java
@Slf4j
@RestController
public class XxxController {
    @RequestMapping("/xxx")
    public Result xxx() {
        log.info("xxx");
    }
}
\`\`\`





































`);

document.title = 'LogBack快速入门'
const show = ref(false);
const form = reactive({
  name: '',
  content: ''
})

onMounted(async () => {
  await commentStore.getCommentList(route.params.id as string);
})
</script>

<template>
  <div class="detail-container">
    <div class="header">
      <h1 class="header-title">Logbcak快速入门</h1>
      <div class="header-meta">
        <div class="icon-list">
          <n-icon size="18">
            <TimeOutline/>
          </n-icon>
          <span v-text="'2025-02-27'"></span>
          <n-icon size="18">
            <EyeOutline/>
          </n-icon>
          <span v-text=" 1"></span>
        </div>
      </div>
    </div>
    <div class="content">
      <MdPreview
          :editorId="id"
          :modelValue="markdownText"
          :editorConfig="{
            extensions: [lineNumbers()]
          }"
      />
    </div>
    <div class="footer">
      更新于 2025-02-27

    </div>
    <n-drawer  v-model:show="show" :block-scroll="false" :default-width="400">
      <n-drawer-content closable >
        <template #header>
          <h4 style="padding-block: 10px">评论 <span style="font-size: 16px">12</span></h4>
        </template>
        <template #default>
          <CommentSection />
        </template>
      </n-drawer-content>
    </n-drawer>

    <div class="article-panel">
      <n-flex vertical :size="20">
        <n-float-button position="relative" style="width: 45px; min-height: 45px">
          <n-badge :value="9" :offset="[6, -8]" color="#aaa">
            <n-icon>
              <HeartOutline/>
            </n-icon>
          </n-badge>
        </n-float-button>
        <n-float-button @click="show = true" position="relative" style="width: 45px; min-height: 45px">
          <n-badge :value="100" :max="99" :offset="[6, -8]" color="#aaa">
            <n-icon>
              <ChatbubbleOutline/>
            </n-icon>
          </n-badge>
        </n-float-button>
      </n-flex>
    </div>
  </div>

  <div class="article-navigation">
    <h3>目 录</h3>
    <MdCatalog :editorId="id" :scrollElement="scrollElement" />
  </div>

</template>

<style scoped>

.detail-container {
  width: 100%;
  padding: 20px 80px;
  @media (max-width: 768px) {
    padding: 20px;
  }
  .header {
    margin-bottom: 30px;

    .header-title {
      padding-block: 10px;
    }

    .header-meta {
      .icon-list {
        display: flex;
        align-items: center;
        color: #666;
        font-size: 14px;
        cursor: auto;

        span {
          margin-right: 10px;
        }

        .n-icon {
          margin-right: 5px;

          &:first-child {
            margin-left: 0;
          }
        }
      }
    }
  }

  .content {
    font-size: 16px;
    color: #4d4d4d;
    width: calc(100%);
  }

  .footer {
    margin-top: 20px;
    color: #aaa;
  }

  .article-panel {
    position: fixed;
    top: 220px;
    margin-left: -9rem;
    @media (max-width: 1140px) {
      margin-left: -4rem;
    }
    @media (max-width: 768px) {
      display: none;
    }
  }
}
:deep(.n-drawer-body) {
  .n-drawer-body-content-wrapper{
    &::-webkit-scrollbar {
      display: none; /* 隐藏滚动条 */
    }
  }

}



.article-navigation {
  top: 180px;
  right: 40px;
  position: fixed;
  width: 200px;
  padding: 20px 0;
  min-height: 150px;
  background-color: #fff;
  @media (max-width: 1500px) {
    display: none;
  }
  h3 {
    margin-left: auto;
    margin-right: auto;
    text-align: center;
    margin-bottom: 10px;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
    width: 80%;
    word-spacing: 10px;
  }
    :deep(.md-editor-catalog-active) {
      span {
        color: #18c28d
      }
    }

    :deep(.md-editor-catalog-indicator) {
      background-color: #18c28d;
    }

    :deep(.md-editor-catalog-link) {
      span {
        &:hover {
          color: #18c28d
        }
        margin-left: 10px;
      }
    }
}
</style>