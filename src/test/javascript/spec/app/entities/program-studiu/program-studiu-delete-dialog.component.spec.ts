/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NohaidragosTestModule } from '../../../test.module';
import { ProgramStudiuDeleteDialogComponent } from 'app/entities/program-studiu/program-studiu-delete-dialog.component';
import { ProgramStudiuService } from 'app/entities/program-studiu/program-studiu.service';

describe('Component Tests', () => {
  describe('ProgramStudiu Management Delete Component', () => {
    let comp: ProgramStudiuDeleteDialogComponent;
    let fixture: ComponentFixture<ProgramStudiuDeleteDialogComponent>;
    let service: ProgramStudiuService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NohaidragosTestModule],
        declarations: [ProgramStudiuDeleteDialogComponent]
      })
        .overrideTemplate(ProgramStudiuDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProgramStudiuDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProgramStudiuService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
