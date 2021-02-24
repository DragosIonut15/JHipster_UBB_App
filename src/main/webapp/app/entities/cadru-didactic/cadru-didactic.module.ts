import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NohaidragosSharedModule } from 'app/shared';
import {
  CadruDidacticComponent,
  CadruDidacticDetailComponent,
  CadruDidacticUpdateComponent,
  CadruDidacticDeletePopupComponent,
  CadruDidacticDeleteDialogComponent,
  cadruDidacticRoute,
  cadruDidacticPopupRoute
} from './';

const ENTITY_STATES = [...cadruDidacticRoute, ...cadruDidacticPopupRoute];

@NgModule({
  imports: [NohaidragosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CadruDidacticComponent,
    CadruDidacticDetailComponent,
    CadruDidacticUpdateComponent,
    CadruDidacticDeleteDialogComponent,
    CadruDidacticDeletePopupComponent
  ],
  entryComponents: [
    CadruDidacticComponent,
    CadruDidacticUpdateComponent,
    CadruDidacticDeleteDialogComponent,
    CadruDidacticDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NohaidragosCadruDidacticModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
