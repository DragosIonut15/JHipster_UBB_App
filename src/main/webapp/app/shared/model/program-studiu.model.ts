import { IDepartament } from 'app/shared/model/departament.model';

export interface IProgramStudiu {
  id?: number;
  program?: string;
  departament?: IDepartament;
}

export class ProgramStudiu implements IProgramStudiu {
  constructor(public id?: number, public program?: string, public departament?: IDepartament) {}
}
