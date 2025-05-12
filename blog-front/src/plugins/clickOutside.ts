// 点击外部 隐藏元素
export const clickOutside = {
	install(app: any) {
		app.directive("click-outside", {
			mounted(el: any, binding: any) {
				// 给元素绑定一个事件
				el.EventLis = function (event: any) {
					if (!el.contains(event.target)) {
						// 如果是外部点击，则执行绑定的函数
						// console.log(event.target);
						// event.stopPropagation();
						binding.value();
					}
				};
				// 在全局添加点击事件监听
				document.addEventListener("click", el.EventLis);
			},
			unmounted(el: any, binding: any) {
				// 在全局移除点击事件监听

				document.removeEventListener("click", el.EventLis);
			},
		});
	},
};
