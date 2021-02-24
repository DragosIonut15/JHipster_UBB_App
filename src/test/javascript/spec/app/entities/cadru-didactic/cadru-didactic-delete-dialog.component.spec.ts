/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NohaidragosTestModule } from '../../../test.module';
import { CadruDidacticDeleteDialogComponent } from 'app/entities/cadru-didactic/cadru-didactic-delete-dialog.component';
import { CadruDidacticService } from 'app/entities/cadru-didactic/cadru-didactic.service';

describe('Component Tests', () => {
  describe('CadruDidactic Management Delete Component', () => {
    let comp: CadruDidacticDeleteDialogComponent;
    let fixture: ComponentFixture<CadruDidacticDeleteDialogComponent>;
    let service: CadruDidacticService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NohaidragosTestModule],
        declarations: [CadruDidacticDeleteDialogComponent]
      })
        .overrideTemplate(CadruDidacticDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CadruDidacticDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CadruDidacticService);
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
