import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {User} from "../interface/user";
import {catchError, tap} from "rxjs/operators";
import {CustomResponse} from "../interface/custom-response";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  users$ = <Observable<CustomResponse>>
    this.http.get<CustomResponse>(`${this.apiUrl}/users`)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );


  save$ = (user: User) => <Observable<CustomResponse>>
    this.http.post<User>(`${this.apiUrl}/users`, user)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

  update$ = (user: User, userId: number) => <Observable<CustomResponse>>
    this.http.put<User>(`${this.apiUrl}/users/${userId}`, user)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

  rentals$ = (userId: number) => <Observable<CustomResponse>>
    this.http.get<User>(`${this.apiUrl}/users/${userId}/rentals`)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

  delete$ = (userId: number) => <Observable<CustomResponse>>
    this.http.delete<CustomResponse>(`${this.apiUrl}/users/delete/${userId}`)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error);
    return throwError(`An error occurred - Error code: ${error.status}`);
  }
}
