import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import { NgForm } from '@angular/forms';
import {BehaviorSubject, Observable, of} from "rxjs";
import {CustomResponse} from "./interface/custom-response";
import {AppState} from "./interface/app-state";
import { DataState } from './enum/data-state.enum';
import {UserService} from "./service/user.service";
import {catchError, map, startWith} from "rxjs/operators";
import {NotificationService} from "./service/notification.service";
import {User} from "./interface/user";
import {CamperService} from "./service/camper.service";
import {Camper} from "./interface/camper";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent implements OnInit{
  public campers: Camper[] = [];
  appState$: Observable<AppState<CustomResponse>>;
  readonly DataState = DataState;
  private filterSubject = new BehaviorSubject<string>('');
  private dataSubject = new BehaviorSubject<CustomResponse>(null);
  private isLoading = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoading.asObservable();

  constructor(private userService: UserService, private camperService: CamperService, private notifier: NotificationService) {}

  ngOnInit(): void {
    this.getCampers();
  }


  private getCampers() {
    this.appState$ = this.camperService.campers$
      .pipe(
        map(response => {
          this.notifier.onDefault(response.message);
          this.dataSubject.next(response);
          return {
            dataState: DataState.LOADED_STATE,
            appData: {...response, data: {servers: response.data.campers.reverse()}}
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

  printReport(): void {
    this.notifier.onDefault('Report downloaded');
    // window.print();
    let dataType = 'application/vnd.ms-excel.sheet.macroEnabled.12';
    let tableSelect = document.getElementById('servers');
    let tableHtml = tableSelect.outerHTML.replace(/ /g, '%20');
    let downloadLink = document.createElement('a');
    document.body.appendChild(downloadLink);
    downloadLink.href = 'data:' + dataType + ', ' + tableHtml;
    downloadLink.download = 'server-report.xls';
    downloadLink.click();
    document.body.removeChild(downloadLink);
  }
}
