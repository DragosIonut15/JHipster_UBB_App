import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Departament } from 'app/shared/model/departament.model';
import { DepartamentService } from './departament.service';
import { DepartamentComponent } from './departament.component';
import { DepartamentDetailComponent } from './departament-detail.component';
import { DepartamentUpdateComponent } from './departament-update.component';
import { DepartamentDeletePopupComponent } from './departament-delete-dialog.component';
import { IDepartament } from 'app/shared/model/departament.model';

@Injectable({ providedIn: 'root' })
export class DepartamentResolve implements Resolve<IDepartament> {
  constructor(private service: DepartamentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDepartament> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Departament>) => response.ok),
        map((departament: HttpResponse<Departament>) => departament.body)
      );
    }
    return of(new Departament());
  }
}

export const departamentRoute: Routes = [
  {
    path: '',
    component: DepartamentComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'nohaidragosApp.departament.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DepartamentDetailComponent,
    resolve: {
      departament: DepartamentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nohaidragosApp.departament.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DepartamentUpdateComponent,
    resolve: {
      departament: DepartamentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nohaidragosApp.departament.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DepartamentUpdateComponent,
    resolve: {
      departament: DepartamentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nohaidragosApp.departament.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const departamentPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DepartamentDeletePopupComponent,
    resolve: {
      departament: DepartamentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nohaidragosApp.departament.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
