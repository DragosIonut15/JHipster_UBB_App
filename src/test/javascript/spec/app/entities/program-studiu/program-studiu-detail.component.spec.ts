/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NohaidragosTestModule } from '../../../test.module';
import { ProgramStudiuDetailComponent } from 'app/entities/program-studiu/program-studiu-detail.component';
import { ProgramStudiu } from 'app/shared/model/program-studiu.model';

describe('Component Tests', () => {
  describe('ProgramStudiu Management Detail Component', () => {
    let comp: ProgramStudiuDetailComponent;
    let fixture: ComponentFixture<ProgramStudiuDetailComponent>;
    const route = ({ data: of({ programStudiu: new ProgramStudiu(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NohaidragosTestModule],
        declarations: [ProgramStudiuDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProgramStudiuDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProgramStudiuDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.programStudiu).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
