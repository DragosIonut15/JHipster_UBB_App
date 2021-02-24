import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'departament',
        loadChildren: './departament/departament.module#NohaidragosDepartamentModule'
      },
      {
        path: 'program-studiu',
        loadChildren: './program-studiu/program-studiu.module#NohaidragosProgramStudiuModule'
      },
      {
        path: 'cadru-didactic',
        loadChildren: './cadru-didactic/cadru-didactic.module#NohaidragosCadruDidacticModule'
      },
      {
        path: 'student',
        loadChildren: './student/student.module#NohaidragosStudentModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NohaidragosEntityModule {}
