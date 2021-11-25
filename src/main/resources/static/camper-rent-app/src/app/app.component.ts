import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import { NgForm } from '@angular/forms';
import {BehaviorSubject, Observable, of} from "rxjs";
import {CustomResponse} from "./interface/custom-response";
import {AppState} from "./interface/app-state";
import { DataState } from './enum/data-state.enum';
import {UserService} from "./service/user.service";
import {catchError, map, startWith} from "rxjs/operators";
import {NotificationService} from "./service/notification.service";
import {CamperService} from "./service/camper.service";
import {Camper} from "./interface/camper";
import {User} from "./interface/user";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent implements OnInit{
  public campers: Camper[] = [];
  public users: User[] = [];
  appState$: Observable<AppState<CustomResponse>>;
  readonly DataState = DataState;
  private dataSubject = new BehaviorSubject<CustomResponse>(null);
  private isLoading = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoading.asObservable();

  constructor(private userService: UserService, private camperService: CamperService, private notifier: NotificationService) {}

  ngOnInit(): void {
    this.getCampers();
  }


  /*public getCampers(): void {
    this.camperService.getCampers().subscribe(
      (response: Camper[]) => {
        this.campers = response;
      },
      (error: HttpErrorResponse) => {
        alert((error.message))
      }
    );
  }*/

  private getCampers() {
    this.appState$ = this.camperService.campers$
      .pipe(
        map(response => {
          this.notifier.onDefault(response.message);
          this.dataSubject.next(response);
          return {
            dataState: DataState.LOADED_STATE,
            appData: {...response, data: {campers: response.data.campers.reverse()}}
          }
        }),
        startWith({dataState: DataState.LOADING_STATE}),
        catchError((error: string) => {
          this.notifier.onError(error);
          return of({dataState: DataState.ERROR_STATE, error});
        })
      );
  }

  saveCamper(camperForm: NgForm): void {
    this.isLoading.next(true);
    this.appState$ = this.camperService.save$(camperForm.value as Camper)
      .pipe(
        map(response => {
          this.dataSubject.next(
            {...response, data: { campers: [response.data.camper, ...this.dataSubject.value.data.campers] } }
          );
          this.notifier.onDefault(response.message);
          document.getElementById('closeModal').click();
          this.isLoading.next(false);
          camperForm.resetForm();
          return { dataState: DataState.LOADED_STATE, appData: this.dataSubject.value }
        }),
        startWith({ dataState: DataState.LOADED_STATE, appData: this.dataSubject.value }),
        catchError((error: string) => {
          this.isLoading.next(false);
          this.notifier.onError(error);
          return of({ dataState: DataState.ERROR_STATE, error });
        })
      );
  }

  public searchCampers(key: string): void {
    console.log(key);
    const results: Camper[] = [];
    for (const camper of this.campers) {
      if (camper.brand.toLowerCase().indexOf(key.toLowerCase()) !== -1 ||
        camper.model.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(camper);
      }
    }
    this.campers = results;
    if (results.length === 0 || !key) {
      this.getCampers();
    }
  }

  deleteCamper(camper: Camper): void {
    this.appState$ = this.camperService.delete$(camper.id)
      .pipe(
        map(response => {
          this.dataSubject.next(
            { ...response, data:
                { campers: this.dataSubject.value.data.campers.filter(s => s.id !== camper.id)} }
          );
          this.notifier.onDefault(response.message);
          return { dataState: DataState.LOADED_STATE, appData: this.dataSubject.value }
        }),
        startWith({ dataState: DataState.LOADED_STATE, appData: this.dataSubject.value }),
        catchError((error: string) => {
          this.notifier.onError(error);
          return of({ dataState: DataState.ERROR_STATE, error });
        })
      );
  }

  private getUsers() {
    this.appState$ = this.userService.users$
      .pipe(
        map(response => {
          this.notifier.onDefault(response.message);
          this.dataSubject.next(response);
          return {
            dataState: DataState.LOADED_STATE,
            appData: {...response, data: {users: response.data.users.reverse()}}
          }
        }),
        startWith({dataState: DataState.LOADING_STATE}),
        catchError((error: string) => {
          this.notifier.onError(error);
          return of({dataState: DataState.ERROR_STATE, error});
        })
      );
  }

  saveUser(userForm: NgForm): void {
    this.isLoading.next(true);
    this.appState$ = this.userService.save$(userForm.value as User)
      .pipe(
        map(response => {
          this.dataSubject.next(
            {...response, data: { campers: [response.data.camper, ...this.dataSubject.value.data.campers] } }
          );
          this.notifier.onDefault(response.message);
          document.getElementById('closeModal').click();
          this.isLoading.next(false);
          userForm.resetForm();
          return { dataState: DataState.LOADED_STATE, appData: this.dataSubject.value }
        }),
        startWith({ dataState: DataState.LOADED_STATE, appData: this.dataSubject.value }),
        catchError((error: string) => {
          this.isLoading.next(false);
          this.notifier.onError(error);
          return of({ dataState: DataState.ERROR_STATE, error });
        })
      );
  }

  public searchUsers(key: string): void {
    console.log(key);
    const results: User[] = [];
    for (const user of this.users) {
      if (user.firstName.toLowerCase().indexOf(key.toLowerCase()) !== -1 ||
        user.lastName.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(user);
      }
    }
    this.users = results;
    if (results.length === 0 || !key) {
      this.getCampers();
    }
  }

  deleteUser(user: User): void {
    this.appState$ = this.userService.delete$(user.id)
      .pipe(
        map(response => {
          this.dataSubject.next(
            { ...response, data:
                { users: this.dataSubject.value.data.users.filter(s => s.id !== user.id)} }
          );
          this.notifier.onDefault(response.message);
          return { dataState: DataState.LOADED_STATE, appData: this.dataSubject.value }
        }),
        startWith({ dataState: DataState.LOADED_STATE, appData: this.dataSubject.value }),
        catchError((error: string) => {
          this.notifier.onError(error);
          return of({ dataState: DataState.ERROR_STATE, error });
        })
      );
  }

  printReport(): void {
    this.notifier.onDefault('Report downloaded');
    // window.print();
    let dataType = 'application/vnd.ms-excel.sheet.macroEnabled.12';
    let tableSelect = document.getElementById('servers');
    let tableHtml = tableSelect.outerHTML.replace(/ /g, '%20');
    let downloadLink = document.createElement('a');
    document.body.appendChild(downloadLink);
    downloadLink.href = 'data:' + dataType + ', ' + tableHtml;
    downloadLink.download = 'camper-report.xls';
    downloadLink.click();
    document.body.removeChild(downloadLink);
  }
}
