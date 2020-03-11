import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NohaidragosSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [NohaidragosSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [NohaidragosSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NohaidragosSharedModule {
  static forRoot() {
    return {
      ngModule: NohaidragosSharedModule
    };
  }
}
