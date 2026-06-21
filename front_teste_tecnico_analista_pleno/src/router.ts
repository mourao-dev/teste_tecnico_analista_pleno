import { createRouter, createWebHistory } from "vue-router";
import CreateTransfer from "./views/CreateTransfer.vue";
import TransferList from "./views/TransferList.vue";

const routes = [
  {
    path: "/",
    name: "create-transfer",
    component: CreateTransfer,
  },
  {
    path: "/transfers",
    name: "transfer-list",
    component: TransferList,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;