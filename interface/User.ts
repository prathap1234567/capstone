
export interface Account {
  accountId?: number;
  accountNumber?: string;
  balance?: number;
  accountStatus?: string;
}


export interface User {
  id?: number;
  fullName: string;
  phone: string;
  userEmail: string;
  userName: string;
  userPass: string;
  userRole?: string;
  failedAttempts?: number;
  accountLocked?: boolean;
  account?: Account;
}