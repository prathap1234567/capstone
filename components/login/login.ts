import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Userservice } from '../../service/userservice';
import { User } from '../../interface/User';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  loginForm!: FormGroup;
  message: string = "";

  constructor(
    private fb: FormBuilder,
    private service: Userservice,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      userEmail: ['', Validators.required],
      userPass: ['', Validators.required]
    });
  }

  doLogin() {

    const user: User = {
      fullName: '',
      phone: '',
      userName: '',
      userEmail: this.loginForm.get('userEmail')?.value,
      userPass: this.loginForm.get('userPass')?.value
    };

    this.service.login(user).subscribe({
    next: (data) => {

  sessionStorage.setItem("userId", data.id);

  if (data.role === "ADMIN") {
    this.router.navigate(['/admin']);
  } else {
    this.router.navigate(['/userdashboard']);
  }
},
      error: (err) => {
        this.message = err.error;
      }
    });
  }
}