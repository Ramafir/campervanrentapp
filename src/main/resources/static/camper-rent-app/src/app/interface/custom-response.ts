import { Camper} from "./camper";
import {User} from "./user";
import {Rental} from "./rental";

export interface CustomResponse {
  timeStamp: Date;
  statusCode: number;
  status: string;
  reason: string;
  message: string;
  developerMessage: string;
  data: { campers?: Camper[], camper?: Camper, users?: User[], user?: User, rentals?: Rental[], renal?: Rental };
}
