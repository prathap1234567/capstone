import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-userdashboard',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './userdashboard.html',
  styleUrl: './userdashboard.css'
})
export class UserDashboard {

  constructor(private router: Router) {}

}