
export interface ArticleCard {
	id: string;
	title: string;
	summary: string;
	thumbnail: string;
	createTime: number;
	viewCount: number;
	likeCount: number;
	tags: string;
}

export interface Article {
	id: string;
	title: string;
	content: string;
	createTime: number;
	updateTime: Date;
	viewCount: number;
	commentCount: number;
	likeCount: number;
}