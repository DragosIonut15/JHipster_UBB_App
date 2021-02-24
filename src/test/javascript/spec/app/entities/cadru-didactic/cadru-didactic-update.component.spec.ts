/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NohaidragosTestModule } from '../../../test.module';
import { CadruDidacticUpdateComponent } from 'app/entities/cadru-didactic/cadru-didactic-update.component';
import { CadruDidacticService } from 'app/entities/cadru-didactic/cadru-didactic.service';
import { CadruDidactic } from 'app/shared/model/cadru-didactic.model';

describe('Component Tests', () => {
  describe('CadruDidactic Management Update Component', () => {
    let comp: CadruDidacticUpdateComponent;
    let fixture: ComponentFixture<CadruDidacticUpdateComponent>;
    let service: CadruDidacticService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NohaidragosTestModule],
        declarations: [CadruDidacticUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CadruDidacticUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CadruDidacticUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CadruDidacticService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CadruDidactic(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CadruDidactic();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
