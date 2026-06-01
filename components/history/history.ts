import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Transaction } from '../../interface/Transaction';
import { Userservice } from '../../service/userservice';
import { Transactionservice } from '../../service/transactionservice';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-history',
  imports: [CommonModule,RouterLink],
  templateUrl: './history.html',
  styleUrl: './history.css',
})
export class History implements OnInit{
   transactions: Transaction[] = [];
  accountNumber: string = "";

  constructor(
    private userService: Userservice,
    private transactionService: Transactionservice,
    private cdr:ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.loadTransactions();
  }

  loadTransactions() {
  const id = Number(sessionStorage.getItem('userId'));

  this.userService.getProfile(id).subscribe({
    next: (user) => {
      this.accountNumber = user.account?.accountNumber || "";

      this.transactionService.getTransactions(this.accountNumber).subscribe({
        next: (data) => {
          this.transactions = data;
          this.cdr.detectChanges();
        }
      });
    }
  });
}
}
