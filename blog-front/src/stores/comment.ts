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

	const reset = () => {
		commentList.value = [];
		hasMore.value = true;
		current = 1;
	}

	return {
		commentList,
		hasMore,

		getCommentList,
		reset
	}
});