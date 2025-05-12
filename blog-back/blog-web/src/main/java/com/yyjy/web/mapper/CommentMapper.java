package com.yyjy.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyjy.web.domain.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyjy.web.domain.vo.request.CommentPageReq;
import com.yyjy.web.domain.vo.response.CommentRes;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author yyjy
 * @since 2025-05-06
 */
public interface CommentMapper extends BaseMapper<Comment> {

	Page<CommentRes> page(Page<CommentRes> commentResPage, CommentPageReq req);
}
