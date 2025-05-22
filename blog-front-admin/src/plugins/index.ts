// Plugins
import {createPinia} from "pinia"
import router from "../router";

import type { App } from 'vue'

export function registerPlugins (app: App) {
    const pinia = createPinia();
    app.use(pinia)
        .use(router)
}