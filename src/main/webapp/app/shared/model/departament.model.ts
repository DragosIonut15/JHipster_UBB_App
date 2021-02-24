import { IProgramStudiu } from 'app/shared/model/program-studiu.model';
import { ICadruDidactic } from 'app/shared/model/cadru-didactic.model';

export interface IDepartament {
  id?: number;
  numeDepartament?: string;
  programStudius?: IProgramStudiu[];
  cadruDidactics?: ICadruDidactic[];
}

export class Departament implements IDepartament {
  constructor(
    public id?: number,
    public numeDepartament?: string,
    public programStudius?: IProgramStudiu[],
    public cadruDidactics?: ICadruDidactic[]
  ) {}
}
