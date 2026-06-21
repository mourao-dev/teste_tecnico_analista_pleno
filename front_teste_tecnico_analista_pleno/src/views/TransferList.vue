<template>
  <div>
    <h2>Consultar Transferências</h2>

    <input v-model="account" placeholder="Conta origem" />
    <button @click="search">Buscar</button>

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

const search = async () => {
  const res = await api.get<TransferResponse[]>(
    `/transfers/${account.value}`
  );

  transfers.value = res.data;
};
</script>