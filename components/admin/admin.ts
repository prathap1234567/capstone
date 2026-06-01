import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { User } from '../../interface/User';
import { Userservice } from '../../service/userservice';


@Component({
  selector: 'app-admin',
  imports: [CommonModule],
  templateUrl: './admin.html',
  styleUrl: './admin.css',
})
export class Admin  implements OnInit {
    users: User[] = [];

  constructor(private service: Userservice,private cdr:ChangeDetectorRef) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.service.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
        this.cdr.detectChanges();
      }
    });
  }

  activate(id: number | undefined) {
    if (!id) return;

    this.service.activateUser(id).subscribe({
      next: () => {
        this.loadUsers();
      }
    });
  }

  deactivate(id: number | undefined) {
    if (!id) return;

    this.service.deactivateUser(id).subscribe({
      next: () => {
        this.loadUsers();
      }
    });
  }

  deleteUser(id: number | undefined) {
    if (!id) return;

    this.service.deleteUser(id).subscribe({
      next: () => {
        this.loadUsers();
      }
    });
  }
}

