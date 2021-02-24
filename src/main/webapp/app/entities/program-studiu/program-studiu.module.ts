import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NohaidragosSharedModule } from 'app/shared';
import {
  ProgramStudiuComponent,
  ProgramStudiuDetailComponent,
  ProgramStudiuUpdateComponent,
  ProgramStudiuDeletePopupComponent,
  ProgramStudiuDeleteDialogComponent,
  programStudiuRoute,
  programStudiuPopupRoute
} from './';

const ENTITY_STATES = [...programStudiuRoute, ...programStudiuPopupRoute];

@NgModule({
  imports: [NohaidragosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProgramStudiuComponent,
    ProgramStudiuDetailComponent,
    ProgramStudiuUpdateComponent,
    ProgramStudiuDeleteDialogComponent,
    ProgramStudiuDeletePopupComponent
  ],
  entryComponents: [
    ProgramStudiuComponent,
    ProgramStudiuUpdateComponent,
    ProgramStudiuDeleteDialogComponent,
    ProgramStudiuDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NohaidragosProgramStudiuModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
