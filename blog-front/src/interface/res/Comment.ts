
export interface Comment {
	id: string;
	nickName: string;
	content: string;
	ipAddress: string;
	isAuthor: number;
	createTime: number;
	parentId: string;
	rootParentId: string;
	replyNickName: string;
	children: Comment[];
}