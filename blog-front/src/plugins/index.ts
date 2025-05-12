// Plugins
import {createPinia} from "pinia"
import router from "../router";
import {clickOutside} from "@/plugins/clickOutside.ts";
import {lazyPlugin} from "@/plugins/lazy.ts";
import {debounceDirective} from "@/plugins/debounce.ts";

// MD Editor
import 'md-editor-v3/lib/preview.css';

import type { App } from 'vue'

export function registerPlugins (app: App) {
	const pinia = createPinia();
	app.use(pinia)
		.use(router)
		.use(clickOutside)
		.use(lazyPlugin)
		.use(debounceDirective)
}
