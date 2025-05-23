export interface ArticleCol {
	id: number;
	title: string;
	tags: string[];
	category: string;
	status: string;
	date: Date[]
}

export interface ArticleDetail {
	title: string;
	content: string;
	summary: string;
	categoryId: number;
	thumbnail: string;
	tags: string[];
}