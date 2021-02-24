/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NohaidragosTestModule } from '../../../test.module';
import { CadruDidacticDetailComponent } from 'app/entities/cadru-didactic/cadru-didactic-detail.component';
import { CadruDidactic } from 'app/shared/model/cadru-didactic.model';

describe('Component Tests', () => {
  describe('CadruDidactic Management Detail Component', () => {
    let comp: CadruDidacticDetailComponent;
    let fixture: ComponentFixture<CadruDidacticDetailComponent>;
    const route = ({ data: of({ cadruDidactic: new CadruDidactic(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NohaidragosTestModule],
        declarations: [CadruDidacticDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CadruDidacticDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CadruDidacticDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cadruDidactic).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
