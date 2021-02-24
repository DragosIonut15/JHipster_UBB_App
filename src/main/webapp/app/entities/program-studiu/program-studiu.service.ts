import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProgramStudiu } from 'app/shared/model/program-studiu.model';

type EntityResponseType = HttpResponse<IProgramStudiu>;
type EntityArrayResponseType = HttpResponse<IProgramStudiu[]>;

@Injectable({ providedIn: 'root' })
export class ProgramStudiuService {
  public resourceUrl = SERVER_API_URL + 'api/program-studius';

  constructor(protected http: HttpClient) {}

  create(programStudiu: IProgramStudiu): Observable<EntityResponseType> {
    return this.http.post<IProgramStudiu>(this.resourceUrl, programStudiu, { observe: 'response' });
  }

  update(programStudiu: IProgramStudiu): Observable<EntityResponseType> {
    return this.http.put<IProgramStudiu>(this.resourceUrl, programStudiu, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProgramStudiu>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProgramStudiu[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
