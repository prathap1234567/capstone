import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { Userservice } from '../../service/userservice';
import { User } from '../../interface/User';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class Register {

  registerForm: FormGroup;

  notSamePassword = false;
  notAgreed = false;
  message = '';

  constructor(
    private fb: FormBuilder,
    private service: Userservice
  ) {
    this.registerForm = this.fb.group({
      fullName: ['', Validators.required],
      phone: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      userEmail: ['', [Validators.required, Validators.email]],
      userName: ['', Validators.required],
      userPass: ['', Validators.required],
      confirmPass: ['', Validators.required],
      terms: [false, Validators.requiredTrue]
    });
  }

  register() {
    this.notSamePassword = false;
    this.notAgreed = false;
    this.message = '';

    const pass = this.registerForm.get('userPass')?.value;
    const confirm = this.registerForm.get('confirmPass')?.value;

    if (pass && confirm && pass !== confirm) {
      this.notSamePassword = true;
      return;
    }

    if (!this.registerForm.get('terms')?.value) {
      this.notAgreed = true;
      return;
    }

    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      this.message = 'Please fill all required fields correctly';
      return;
    }

    const user: User = {
      fullName: this.registerForm.get('fullName')?.value,
      phone: this.registerForm.get('phone')?.value,
      userEmail: this.registerForm.get('userEmail')?.value,
      userName: this.registerForm.get('userName')?.value,
      userPass: this.registerForm.get('userPass')?.value
    };

    this.service.register(user).subscribe({
      next: (data) => {
        this.message = data;
        this.registerForm.reset();
      },
      error: (err) => {
        this.message = err.error;
      }
    });
  }
}