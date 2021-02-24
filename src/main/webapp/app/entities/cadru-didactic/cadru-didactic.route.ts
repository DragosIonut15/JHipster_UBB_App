import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CadruDidactic } from 'app/shared/model/cadru-didactic.model';
import { CadruDidacticService } from './cadru-didactic.service';
import { CadruDidacticComponent } from './cadru-didactic.component';
import { CadruDidacticDetailComponent } from './cadru-didactic-detail.component';
import { CadruDidacticUpdateComponent } from './cadru-didactic-update.component';
import { CadruDidacticDeletePopupComponent } from './cadru-didactic-delete-dialog.component';
import { ICadruDidactic } from 'app/shared/model/cadru-didactic.model';

@Injectable({ providedIn: 'root' })
export class CadruDidacticResolve implements Resolve<ICadruDidactic> {
  constructor(private service: CadruDidacticService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICadruDidactic> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CadruDidactic>) => response.ok),
        map((cadruDidactic: HttpResponse<CadruDidactic>) => cadruDidactic.body)
      );
    }
    return of(new CadruDidactic());
  }
}

export const cadruDidacticRoute: Routes = [
  {
    path: '',
    component: CadruDidacticComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'nohaidragosApp.cadruDidactic.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CadruDidacticDetailComponent,
    resolve: {
      cadruDidactic: CadruDidacticResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nohaidragosApp.cadruDidactic.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CadruDidacticUpdateComponent,
    resolve: {
      cadruDidactic: CadruDidacticResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nohaidragosApp.cadruDidactic.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CadruDidacticUpdateComponent,
    resolve: {
      cadruDidactic: CadruDidacticResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nohaidragosApp.cadruDidactic.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cadruDidacticPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CadruDidacticDeletePopupComponent,
    resolve: {
      cadruDidactic: CadruDidacticResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nohaidragosApp.cadruDidactic.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
