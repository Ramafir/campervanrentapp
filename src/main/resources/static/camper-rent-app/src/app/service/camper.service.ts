import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {CustomResponse} from "../interface/custom-response";
import {catchError, tap} from "rxjs/operators";
import {User} from "../interface/user";
import {Camper} from "../interface/camper";

@Injectable({
  providedIn: 'root'
})
export class CamperService {

  private readonly apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  campers$ = <Observable<CustomResponse>>
    this.http.get<CustomResponse>(`${this.apiUrl}/users`)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );


  save$ = (camper: Camper) => <Observable<CustomResponse>>
    this.http.post<User>(`${this.apiUrl}/campers`, camper)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

  update$ = (camper: Camper, camperId: number) => <Observable<CustomResponse>>
    this.http.put<User>(`${this.apiUrl}/campers/${camperId}`, camper)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

  rental$ = (camperId: number) => <Observable<CustomResponse>>
    this.http.get<User>(`${this.apiUrl}/campers/${camperId}/rentals`)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

  delete$ = (camperId: number) => <Observable<CustomResponse>>
    this.http.delete<CustomResponse>(`${this.apiUrl}/campers/delete/${camperId}`)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error);
    return throwError(`An error occurred - Error code: ${error.status}`);
  }
}
