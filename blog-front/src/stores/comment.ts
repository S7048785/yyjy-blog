import {defineStore} from "pinia";
import type {Comment} from "@/interface/res/Comment.ts";
import request from "@/utils/request.ts";

export const useCommentStore = defineStore('comment', () => {

	const commentList = ref<Comment[]>([]);
	let current = 1;
	const size = 5;
	let hasMore = ref(true);
	const getCommentList = async (articleId: string) => {

		if (!hasMore.value) {
			return;
		}
		const res: any = await request.get('/comment/list', {
			params: {
				articleId,
				current,
				size
			}
		})
		current++;
		commentList.value.push(...res.records);
		if (commentList.value.length === res.total) {
			hasMore.value = false;
			return;
		}
	};

	/**
	 * 清空评论区
	 */
	const reset = () => {
		commentList.value = [];
		hasMore.value = true;
		current = 1;
	}

	// 当前回复的评论索引
	const replyIndex = ref('');

	return {
		commentList,
		hasMore,
		replyIndex,
		getCommentList,
		reset
	}
});