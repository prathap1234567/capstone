import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { IPayee } from '../../interface/Transaction';
import { Userservice } from '../../service/userservice';
import { Transactionservice } from '../../service/transactionservice';
import { User } from '../../interface/User';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-transfer',
  imports: [CommonModule, ReactiveFormsModule,RouterLink],
  templateUrl: './transfer.html',
  styleUrl: './transfer.css',
})
export class Transfer implements OnInit {
  
  transferForm!: FormGroup;
  payees: IPayee[] = [];
  accountNumber: string = "";
  message: string = "";

  constructor(
    private fb: FormBuilder,
    private userService: Userservice,
    private transactionService: Transactionservice
  ) {
    this.transferForm = this.fb.group({
      toAccount: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(1)]],
      remarks: ['']
    });
  }

  ngOnInit(): void {
    this.loadUserAndPayees();
  }

  loadUserAndPayees() {
   
  const id = Number(sessionStorage.getItem('userId'));

  this.userService.getProfile(id).subscribe({
    next: (user: User) => {
      this.accountNumber = user.account?.accountNumber || '';

      this.transactionService.getPayees(this.accountNumber).subscribe({
        next: (data) => {
          this.payees = data;
        }
      });
    }
  });


  }

  transferMoney() {
    const request = {
      fromAccount: this.accountNumber,
      toAccount: this.transferForm.get('toAccount')?.value,
      amount: this.transferForm.get('amount')?.value,
      remarks: this.transferForm.get('remarks')?.value
    };

    this.transactionService.transferMoney(request).subscribe({
      next: (data) => {
        this.message = "Transaction created with status: " + data.status;
        this.transferForm.reset();
      },
      error: (err) => {
        this.message = err.error;
      }
    });
  }
}
