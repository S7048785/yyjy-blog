import dayjs from 'dayjs'
import RelativeTime from 'dayjs/plugin/relativeTime'
import 'dayjs/locale/zh-cn' // 导入本地化语言
dayjs.extend(RelativeTime)
dayjs.locale('zh-cn') // 使用本地化语言
/**
 * 将时间戳转换为相对时间描述（如"3天前"）
 * @param timestamp 目标时间的时间戳（秒秒数）
 * @returns 相对时间描述字符串
 */
export function formatRelativeTime(timestamp: number) {
	return dayjs().to(dayjs(new Date(timestamp as number * 1000)))
}