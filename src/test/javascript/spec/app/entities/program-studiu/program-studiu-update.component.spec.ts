/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NohaidragosTestModule } from '../../../test.module';
import { ProgramStudiuUpdateComponent } from 'app/entities/program-studiu/program-studiu-update.component';
import { ProgramStudiuService } from 'app/entities/program-studiu/program-studiu.service';
import { ProgramStudiu } from 'app/shared/model/program-studiu.model';

describe('Component Tests', () => {
  describe('ProgramStudiu Management Update Component', () => {
    let comp: ProgramStudiuUpdateComponent;
    let fixture: ComponentFixture<ProgramStudiuUpdateComponent>;
    let service: ProgramStudiuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NohaidragosTestModule],
        declarations: [ProgramStudiuUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProgramStudiuUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProgramStudiuUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProgramStudiuService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProgramStudiu(123);
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
        const entity = new ProgramStudiu();
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
