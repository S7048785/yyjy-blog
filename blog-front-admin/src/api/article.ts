import request from "@/utils/request.ts";
import type {ArticleCol} from "@/interface/request/article.ts";
import type {ArticleDetail} from "@/interface/response/article.ts";

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
 * 获取文章详情
 * @param id
 */
export const getArticleDetail = (id: number) => {
  return request.get(`/article/${id}`)
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

/**
 * 保存文章
 * @param data
 */
export const saveArticle = (data: any) => {
  return request.post('/article', data)
}

/**
 * 更新文章
 * @param data
 */
export const updateArticle = (data: ArticleDetail) => {
  return request({
    url: `/article`,
    method: 'put',
    data: data
  })
  // return request.put(`/article/${data.id}`, data)
}