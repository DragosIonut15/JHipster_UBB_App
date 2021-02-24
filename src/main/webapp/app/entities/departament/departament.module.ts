import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NohaidragosSharedModule } from 'app/shared';
import {
  DepartamentComponent,
  DepartamentDetailComponent,
  DepartamentUpdateComponent,
  DepartamentDeletePopupComponent,
  DepartamentDeleteDialogComponent,
  departamentRoute,
  departamentPopupRoute
} from './';

const ENTITY_STATES = [...departamentRoute, ...departamentPopupRoute];

@NgModule({
  imports: [NohaidragosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DepartamentComponent,
    DepartamentDetailComponent,
    DepartamentUpdateComponent,
    DepartamentDeleteDialogComponent,
    DepartamentDeletePopupComponent
  ],
  entryComponents: [DepartamentComponent, DepartamentUpdateComponent, DepartamentDeleteDialogComponent, DepartamentDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NohaidragosDepartamentModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
