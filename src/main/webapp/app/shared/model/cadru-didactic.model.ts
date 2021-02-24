import { IDepartament } from 'app/shared/model/departament.model';
import { IStudent } from 'app/shared/model/student.model';

export interface ICadruDidactic {
  id?: number;
  nume?: string;
  prenume?: string;
  titlu?: string;
  email?: string;
  birou?: number;
  departament?: IDepartament;
  students?: IStudent[];
}

export class CadruDidactic implements ICadruDidactic {
  constructor(
    public id?: number,
    public nume?: string,
    public prenume?: string,
    public titlu?: string,
    public email?: string,
    public birou?: number,
    public departament?: IDepartament,
    public students?: IStudent[]
  ) {}
}
