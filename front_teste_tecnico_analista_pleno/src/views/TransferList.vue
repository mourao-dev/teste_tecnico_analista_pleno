<template>
  <div>
    <h2>Consultar Transferências</h2>

    <input v-model="account" placeholder="Conta origem" />
    <button @click="search">Buscar</button>

    <p v-if="message" class="error">
      {{ message }}
    </p>

    <!-- Tabela -->
    <table v-if="transfers.length">
      <thead>
        <tr>
          <th>Origem</th>
          <th>Destino</th>
          <th>Valor</th>
          <th>Taxa</th>
          <th>Data</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="t in transfers" :key="t.id">
          <td>{{ t.originAccount }}</td>
          <td>{{ t.destinationAccount }}</td>
          <td>{{ t.amount }}</td>
          <td>{{ t.fee }}</td>
          <td>{{ t.transferDate }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { api } from "../services/api";
import type { TransferResponse } from "../types/transfer";

const account = ref<string>("");
const transfers = ref<TransferResponse[]>([]);
const message = ref<string>("");

const search = async () => {
  try {
    message.value = "";

    const res = await api.get<TransferResponse[]>(
      `/transfers/${account.value}`
    );

    transfers.value = res.data;
  } catch (err: any) {
  transfers.value = [];

  if (err.response?.status === 404) {
    message.value = "Conta não encontrada";
    return;
  }

  message.value =
    err.response?.data?.message ||
    "Erro ao buscar transferências";
}
};
</script>

<style scoped>
.error {
  color: red;
  margin-top: 10px;
  font-weight: bold;
}
</style>