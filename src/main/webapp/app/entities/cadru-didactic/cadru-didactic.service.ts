import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICadruDidactic } from 'app/shared/model/cadru-didactic.model';

type EntityResponseType = HttpResponse<ICadruDidactic>;
type EntityArrayResponseType = HttpResponse<ICadruDidactic[]>;

@Injectable({ providedIn: 'root' })
export class CadruDidacticService {
  public resourceUrl = SERVER_API_URL + 'api/cadru-didactics';

  constructor(protected http: HttpClient) {}

  create(cadruDidactic: ICadruDidactic): Observable<EntityResponseType> {
    return this.http.post<ICadruDidactic>(this.resourceUrl, cadruDidactic, { observe: 'response' });
  }

  update(cadruDidactic: ICadruDidactic): Observable<EntityResponseType> {
    return this.http.put<ICadruDidactic>(this.resourceUrl, cadruDidactic, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICadruDidactic>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICadruDidactic[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
