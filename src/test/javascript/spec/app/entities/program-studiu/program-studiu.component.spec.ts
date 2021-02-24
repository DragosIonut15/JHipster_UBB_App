/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NohaidragosTestModule } from '../../../test.module';
import { ProgramStudiuComponent } from 'app/entities/program-studiu/program-studiu.component';
import { ProgramStudiuService } from 'app/entities/program-studiu/program-studiu.service';
import { ProgramStudiu } from 'app/shared/model/program-studiu.model';

describe('Component Tests', () => {
  describe('ProgramStudiu Management Component', () => {
    let comp: ProgramStudiuComponent;
    let fixture: ComponentFixture<ProgramStudiuComponent>;
    let service: ProgramStudiuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NohaidragosTestModule],
        declarations: [ProgramStudiuComponent],
        providers: []
      })
        .overrideTemplate(ProgramStudiuComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProgramStudiuComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProgramStudiuService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProgramStudiu(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.programStudius[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
