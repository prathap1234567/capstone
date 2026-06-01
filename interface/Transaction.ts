export interface IPayee {
  payeeId?: number;
  userAccountNumber: string;
  payeeName: string;
  payeeAccountNumber: string;
  bankName: string;
}

export interface Transaction {
  transactionId?: number;
  fromAccount: string;
  toAccount: string;
  amount: number;
  remarks: string;
  transactionType?: string;
  status?: string;
  createdAt?: string;
}

export interface TransferRequest {
  fromAccount: string;
  toAccount: string;
  amount: number;
  remarks: string;
}