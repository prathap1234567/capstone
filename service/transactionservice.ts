import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IPayee, Transaction, TransferRequest } from '../interface/Transaction';


@Injectable({
  providedIn: 'root'
})
export class Transactionservice {

  baseUrl = "http://localhost:8082/transactions";

  constructor(private http: HttpClient) {}

  addPayee(payee: IPayee): Observable<IPayee> {
    return this.http.post<IPayee>(
      this.baseUrl + "/payee/add",
      payee,
      { withCredentials: true }
    );
  }

  getPayees(accountNumber: string): Observable<IPayee[]> {
    return this.http.get<IPayee[]>(
      this.baseUrl + "/payee/" + accountNumber,
      { withCredentials: true }
    );
  }

  deletePayee(id: number): Observable<any> {
    return this.http.delete(
      this.baseUrl + "/payee/delete/" + id,
      {
        withCredentials: true,
        responseType: 'text'
      }
    );
  }

  transferMoney(req: TransferRequest): Observable<Transaction> {
    return this.http.post<Transaction>(
      this.baseUrl + "/transfer",
      req,
      { withCredentials: true }
    );
  }

  getTransactions(accountNumber: string): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(
      this.baseUrl + "/account/" + accountNumber,
      { withCredentials: true }
    );
  }
}