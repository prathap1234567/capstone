import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { User } from '../../interface/User';
import { Userservice } from '../../service/userservice';
import { Router } from '@angular/router';

@Component({
  selector: 'app-updateprof',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './updateprof.html',
  styleUrl: './updateprof.css',
})
export class Updateprof implements OnInit{
   user!: User;

  profileForm!: FormGroup;
  passwordForm!: FormGroup;
  moneyForm!: FormGroup;

  message: string = "";

  constructor(
    private service: Userservice,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.profileForm = this.fb.group({
      fullName: ['', Validators.required],
      phone: ['', Validators.required],
      userName: ['', Validators.required],
      userEmail: [{ value: '', disabled: true }]
    });

    this.passwordForm = this.fb.group({
      oldPassword: ['', Validators.required],
      newPassword: ['', Validators.required]
    });

    this.moneyForm = this.fb.group({
      amount: ['', [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    this.loadProfile();
  }
  getUserId(): number {
  return Number(sessionStorage.getItem("userId"));
}
loadProfile() {
  const id = this.getUserId();

  this.service.getProfile(id).subscribe({
    next: (data) => {
      this.user = data;

      this.profileForm.patchValue({
        fullName: data.fullName,
        phone: data.phone,
        userName: data.userName,
        userEmail: data.userEmail
      });
    },
    error: () => {
      this.router.navigate(['/login']);
    }
  });
}
updateProfile() {
  const id = this.getUserId();

  this.service.updateProfile(id, this.profileForm.getRawValue()).subscribe({
    next: () => {
      
      this.loadProfile();
    }
  });
}
changePassword() {
  const id = this.getUserId();

  this.service.changePassword(id, this.passwordForm.value).subscribe({
    next: (data) => {
      this.message = data;
      this.passwordForm.reset();
    },
    error: (err) => {
      this.message = err.error;
    }
  });
}
loadMoney() {
  const id = this.getUserId();

  this.service.loadMoney(id, this.moneyForm.value).subscribe({
    next: () => {
     
      this.moneyForm.reset();
      this.loadProfile();
    }
  });
}

  
}
