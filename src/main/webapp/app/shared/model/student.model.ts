import { ICadruDidactic } from 'app/shared/model/cadru-didactic.model';

export const enum LiniaDeStudiu {
  ROMANA = 'ROMANA',
  ENGLEZA = 'ENGLEZA',
  MAGHIARA = 'MAGHIARA',
  GERMANA = 'GERMANA'
}

export const enum FormaInvatamant {
  ID = 'ID',
  IF = 'IF'
}

export interface IStudent {
  id?: number;
  nume?: string;
  prenume?: string;
  numarMatricol?: number;
  lucrareLicenta?: string;
  linia?: LiniaDeStudiu;
  forma?: FormaInvatamant;
  cadruDidactic?: ICadruDidactic;
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public nume?: string,
    public prenume?: string,
    public numarMatricol?: number,
    public lucrareLicenta?: string,
    public linia?: LiniaDeStudiu,
    public forma?: FormaInvatamant,
    public cadruDidactic?: ICadruDidactic
  ) {}
}
