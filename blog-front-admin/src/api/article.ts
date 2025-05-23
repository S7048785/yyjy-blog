import request from "@/utils/request.ts";
import type {ArticleCol} from "@/interface/request/article.ts";

/**
 * 获取文章列表
 * @param params
 */
export const getArticleList = (params: ArticleCol) => {
  return request.get('/article/list', {
    params: {
      id: params.id,
      title: params.title,
      category: params.category,
      status: params.status,
      begin: new Date(params.date[0]).toLocaleDateString(),
      end: new Date(params.date[1]).toLocaleDateString(),
      tags: params.tags.join(',')
    }
  })
}

/**
 * 删除文章
 * @param id
 */
export const deleteArticle = (id: number) => {
  return request.delete(`/article/${id}`)
}

/**
 * 批量删除文章
 * @param ids
 */
export const deleteArticleBatch = (ids: number[]) => {
  return request.delete('/article/batch', {
    data: ids
  })
}

export const saveArticle = (data: any) => {
  return request.post('/article', data)
}