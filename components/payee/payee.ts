import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { User } from '../../interface/User';
import { IPayee } from '../../interface/Transaction';

import { Transactionservice } from '../../service/transactionservice';
import { Userservice } from '../../service/userservice';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-payee',
  imports: [CommonModule, ReactiveFormsModule,RouterLink],
  templateUrl: './payee.html',
  styleUrl: './payee.css',
})
export class Payee implements OnInit {

  payeeForm!: FormGroup;
  payees: IPayee[] = [];
  accountNumber: string = '';
  message: string = '';

  constructor(
    private fb: FormBuilder,
    private transactionService: Transactionservice,
    private userService: Userservice,
    private cdr:ChangeDetectorRef,
  ) {
    this.payeeForm = this.fb.group({
      payeeName: ['', Validators.required],
      payeeAccountNumber: ['', Validators.required],
      bankName: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadCurrentUser();
  }

  loadCurrentUser(): void {
    const id = Number(sessionStorage.getItem('userId'));

this.userService.getProfile(id).subscribe({
  next: (user: User) => {
     console.log("FULL USER =", user);
      console.log("ACCOUNT =", user.account);
      console.log("ACCOUNT NUMBER =", user.account?.accountNumber);
    this.accountNumber = user.account?.accountNumber || '';
    this.loadPayees();
  },
    error: (err) => {
      console.log(err);
    }

});
  }

  loadPayees(): void {
    this.transactionService.getPayees(this.accountNumber).subscribe({
      next: (data: IPayee[]) => {
        this.payees = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  addPayee(): void {

    if (this.payeeForm.invalid) {
      return;
    }

    const payee: IPayee = {
      userAccountNumber: this.accountNumber,
      payeeName: this.payeeForm.value.payeeName,
      payeeAccountNumber: this.payeeForm.value.payeeAccountNumber,
      bankName: this.payeeForm.value.bankName
    };

    this.transactionService.addPayee(payee).subscribe({
      next: () => {
        this.message = 'Payee added successfully';
        this.payeeForm.reset();
        this.loadPayees();
      },
      error: (err) => {
        this.message = err.error;
      }
    });
  }

  deletePayee(id: number | undefined): void {

    if (!id) {
      return;
    }

    this.transactionService.deletePayee(id).subscribe({
      next: () => {
        this.message = 'Payee deleted successfully';
        this.loadPayees();
      },
      error: (err) => {
        console.error(err);
      }
    });
  }
}