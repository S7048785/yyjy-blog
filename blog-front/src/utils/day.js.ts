const intervals = {
	year: 31536000,
	month: 2592000,
	week: 604800,
	day: 86400,
	hour: 3600,
	minute: 60,
	second: 1
};

/**
 * 将时间戳转换为相对时间描述（如"3天前"）
 * @param timestamp 目标时间的时间戳（秒秒数）
 * @returns 相对时间描述字符串
 */
export function formatRelativeTime(timestamp: number) {
	// 当前时间对象
	const now = Date.now() / 1000;

	// 时间差（秒）
	const diffInSeconds = Math.ceil((now - timestamp));

	// 国际化相对时间格式化器（中文环境）
	const rtf = new Intl.RelativeTimeFormat('zh', { numeric: 'auto' });

	// 遍历时间单位计算相对时间
	for (const [unit, seconds] of Object.entries(intervals)) {
		const interval = Math.floor(diffInSeconds / seconds);
		if (interval >= 1) {
			return rtf.format(-interval, unit as any);
		}
	}

	// 小于1秒的时间差
	return '刚刚';
}

// 使用示例
// console.log(formatRelativeTime(Date.now() - 86400 * 1000 * 3)); // 输出 "3天前"