import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepartament } from 'app/shared/model/departament.model';
import { DepartamentService } from './departament.service';

@Component({
  selector: 'jhi-departament-delete-dialog',
  templateUrl: './departament-delete-dialog.component.html'
})
export class DepartamentDeleteDialogComponent {
  departament: IDepartament;

  constructor(
    protected departamentService: DepartamentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.departamentService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'departamentListModification',
        content: 'Deleted an departament'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-departament-delete-popup',
  template: ''
})
export class DepartamentDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ departament }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DepartamentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.departament = departament;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/departament', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/departament', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
