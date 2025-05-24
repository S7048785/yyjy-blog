export interface ArticleColumn {
	id: number;
	title: string;
	category: string;
	tags: string[];
	date: string;
	status: string;
}

export interface ArticleDetail {
	id: string | number;
	title: string;
	content: string;
	summary: string;
	categoryId: string | null;
	thumbnail: string | null;
	status: string | null;
	tags: string | string[];
}