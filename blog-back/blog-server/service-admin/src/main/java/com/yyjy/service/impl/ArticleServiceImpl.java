package com.yyjy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyjy.dao.ArticleDao;
import com.yyjy.dao.ArticleTagDao;
import com.yyjy.dao.TagDao;
import com.yyjy.domain.entity.Article;
import com.yyjy.domain.entity.ArticleTag;
import com.yyjy.domain.entity.Tag;
import com.yyjy.domain.vo.request.ArticleDetailReq;
import com.yyjy.domain.vo.request.ArticleListReq;
import com.yyjy.domain.vo.response.ArticleColRes;
import com.yyjy.domain.vo.response.ArticleDetailRes;
import com.yyjy.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Resource
	private ArticleDao articleDao;
	@Resource
	private ArticleTagDao articleTagDao;
	@Resource
	private TagDao tagDao;

	/**
	 * 获取文章列表
	 * @param req
	 */
	@Override
	public List<ArticleColRes> list(ArticleListReq req) {
		return articleDao.list(req);
	}

	/**
	 * 删除文章
	 * @param id
	 */
	@Override
	public Boolean removeById(Long id) {
		return articleDao.update(Wrappers.lambdaUpdate(Article.class)
				.eq(Article::getId, id)
				.set(Article::getDelFlag, 1));
		// TODO 删除缓存
	}

	/**
	 * 批量删除文章
	 * @param ids
	 */
	@Override
	public Boolean removeBatchByIds(List<Long> ids) {
		return articleDao.update(Wrappers.lambdaUpdate(Article.class)
				.set(Article::getDelFlag, 1)
				.in(Article::getId, ids));

		// TODO 更新缓存
	}

	/**
	 * 保存文章及其标签信息
	 *
	 * 处理流程：
	 * 1. 从请求中提取标签列表并转换为Tag对象列表
	 * 2. 批量保存或更新标签数据
	 * 3. 将请求参数转换为Article对象并保存
	 *
	 * @param req 文章详情请求对象，包含文章内容和标签列表
	 */
	@Transactional
	@Override
	public void save(ArticleDetailReq req) {
		// 处理标签数据：将字符串标签列表转换为Tag对象列表
		List<String> tags = req.getTags();

		List<Tag> tagList = new ArrayList<>();

		tags.forEach(tagName -> {
			tagList.add(new Tag().setName(tagName));
		});

		// 批量保存或更新标签数据
		tagDao.saveOrUpdateBatch(tagList);

		// 转换并保存文章主体信息
		Article article = BeanUtil.copyProperties(req, Article.class);
		article.setCreateTime(String.valueOf(Math.floor(System.currentTimeMillis() / 1000)));
		articleDao.save(article);

		List<ArticleTag> articleTagList = new ArrayList<>(tagList.size());
		tagList.forEach(tag -> {
			articleTagList.add(new ArticleTag().setArticleId(article.getId()).setTagId(tag.getId()));
		});
		articleTagDao.saveBatch(articleTagList);

		// TODO 更新缓存
	}

	/**
	 * 获取文章详情
	 * @param id
	 * @return
	 */
	@Override
	public ArticleDetailRes getById(Long id) {
		return articleDao.getById(id);
	}

	/**
	 * 修改文章及其标签信息
	 * @param req
	 */
	@Transactional
	@Override
	public void updateById(ArticleDetailReq req) {
		// 查询所有标签
		List<Tag> list = tagDao.list(Wrappers.lambdaQuery(Tag.class));
		List<String> tagAll = list.stream().map(item -> item.getName().toLowerCase()).toList();

		// 删除该文章的所有标签关系
		articleTagDao.remove(Wrappers.lambdaQuery(ArticleTag.class)
				.eq(ArticleTag::getArticleId, req.getId()));

		// 更新文章标签
		List<Tag> tagList = new ArrayList<>();
		List<String> tagList1 = new ArrayList<>();
		req.getTags().forEach(tagName -> {
			if (!tagAll.contains(tagName.toLowerCase())) {
				tagList.add(new Tag().setName(tagName));
			} else {
				tagList1.add(tagName);
			}
		});
		tagDao.saveOrUpdateBatch(tagList);

		List<Tag> list1 = tagDao.list(Wrappers.lambdaQuery(Tag.class).in(Tag::getName, tagList1));
		articleTagDao.saveBatch(list1.stream().map(tag -> new ArticleTag().setArticleId(req.getId()).setTagId(tag.getId())).toList());

		articleTagDao.saveBatch(tagList.stream().map(tag -> new ArticleTag().setArticleId(req.getId()).setTagId(tag.getId())).toList());

		Article article = BeanUtil.copyProperties(req, Article.class);
		articleDao.updateById(article);

		// TODO 删除缓存
	}
}
