import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../interface/User';


@Injectable({
  providedIn: 'root'
})
export class Userservice {

  baseUrl: string = "http://localhost:8081/user";

  constructor(private http: HttpClient) {}

  register(user: User): Observable<any> {
    return this.http.post(this.baseUrl + "/register", user, {
      responseType: 'text'
    });
  }

 login(user: User): Observable<any> {
  return this.http.post(
    this.baseUrl + "/login",
    user,
    { withCredentials: true }
  );
}

getAllUsers(): Observable<User[]> {
  return this.http.get<User[]>(
    this.baseUrl + "/admin/users",
    { withCredentials: true }
  );
}

activateUser(id: number): Observable<User> {
  return this.http.put<User>(
    this.baseUrl + "/admin/activate/" + id,
    {},
    { withCredentials: true }
  );
}

deactivateUser(id: number): Observable<User> {
  return this.http.put<User>(
    this.baseUrl + "/admin/deactivate/" + id,
    {},
    { withCredentials: true }
  );
}

deleteUser(id: number): Observable<any> {
  return this.http.delete(
    this.baseUrl + "/admin/delete/" + id,
    {
      withCredentials: true,
      responseType: 'text'
    }
  );
}
getProfile(id: number): Observable<User> {
  return this.http.get<User>(
    this.baseUrl + "/profile/" + id,
    { withCredentials: true }
  );
}

updateProfile(id: number, data: any): Observable<User> {
  return this.http.put<User>(
    this.baseUrl + "/profile/" + id,
    data,
    { withCredentials: true }
  );
}

changePassword(id: number, data: any): Observable<any> {
  return this.http.put(
    this.baseUrl + "/change-password/" + id,
    data,
    {
      withCredentials: true,
      responseType: 'text'
    }
  );
}

loadMoney(id: number, data: any): Observable<User> {
  return this.http.put<User>(
    this.baseUrl + "/load-money/" + id,
    data,
    { withCredentials: true }
  );
}

}