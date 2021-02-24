import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProgramStudiu } from 'app/shared/model/program-studiu.model';
import { ProgramStudiuService } from './program-studiu.service';
import { ProgramStudiuComponent } from './program-studiu.component';
import { ProgramStudiuDetailComponent } from './program-studiu-detail.component';
import { ProgramStudiuUpdateComponent } from './program-studiu-update.component';
import { ProgramStudiuDeletePopupComponent } from './program-studiu-delete-dialog.component';
import { IProgramStudiu } from 'app/shared/model/program-studiu.model';

@Injectable({ providedIn: 'root' })
export class ProgramStudiuResolve implements Resolve<IProgramStudiu> {
  constructor(private service: ProgramStudiuService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProgramStudiu> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProgramStudiu>) => response.ok),
        map((programStudiu: HttpResponse<ProgramStudiu>) => programStudiu.body)
      );
    }
    return of(new ProgramStudiu());
  }
}

export const programStudiuRoute: Routes = [
  {
    path: '',
    component: ProgramStudiuComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nohaidragosApp.programStudiu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProgramStudiuDetailComponent,
    resolve: {
      programStudiu: ProgramStudiuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nohaidragosApp.programStudiu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProgramStudiuUpdateComponent,
    resolve: {
      programStudiu: ProgramStudiuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nohaidragosApp.programStudiu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProgramStudiuUpdateComponent,
    resolve: {
      programStudiu: ProgramStudiuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nohaidragosApp.programStudiu.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const programStudiuPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProgramStudiuDeletePopupComponent,
    resolve: {
      programStudiu: ProgramStudiuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nohaidragosApp.programStudiu.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
