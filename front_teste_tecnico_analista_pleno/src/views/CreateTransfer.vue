<template>
  <div>
    <h2>Criar Transferência</h2>

    <form @submit.prevent="submit">
      <input v-model="form.originAccount" placeholder="Conta origem" />
      <input v-model="form.destinationAccount" placeholder="Conta destino" />
      <input v-model.number="form.amount" type="number" placeholder="Valor" />
      <input v-model="form.transferDate" type="date" />

      <button type="submit">Agendar</button>
    </form>

    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import { api } from "../services/api";
import type { TransferRequest } from "../types/transfer";

const form = reactive<TransferRequest>({
  originAccount: "",
  destinationAccount: "",
  amount: 0,
  transferDate: ""
});

const message = ref<string>("");

const submit = async () => {
  try {
    await api.post("/transfers", form);
    message.value = "Transferência agendada com sucesso!";
  } catch (err: any) {
    message.value =
      err.response?.data?.message || "Erro ao criar transferência";
  }
};
</script>