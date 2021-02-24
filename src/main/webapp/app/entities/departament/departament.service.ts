import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDepartament } from 'app/shared/model/departament.model';

type EntityResponseType = HttpResponse<IDepartament>;
type EntityArrayResponseType = HttpResponse<IDepartament[]>;

@Injectable({ providedIn: 'root' })
export class DepartamentService {
  public resourceUrl = SERVER_API_URL + 'api/departaments';

  constructor(protected http: HttpClient) {}

  create(departament: IDepartament): Observable<EntityResponseType> {
    return this.http.post<IDepartament>(this.resourceUrl, departament, { observe: 'response' });
  }

  update(departament: IDepartament): Observable<EntityResponseType> {
    return this.http.put<IDepartament>(this.resourceUrl, departament, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDepartament>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDepartament[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
