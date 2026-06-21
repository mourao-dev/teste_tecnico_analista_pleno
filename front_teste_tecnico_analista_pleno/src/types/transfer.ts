
export interface TransferRequest {
  originAccount: string;
  destinationAccount: string;
  amount: number;
  transferDate: string;
}

export interface TransferResponse {
  id: number;
  originAccount: string;
  destinationAccount: string;
  amount: number;
  fee: number;
  transferDate: string;
  appointmentDate: string;
}