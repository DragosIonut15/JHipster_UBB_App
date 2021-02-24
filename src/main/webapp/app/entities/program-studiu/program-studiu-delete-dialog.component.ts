import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProgramStudiu } from 'app/shared/model/program-studiu.model';
import { ProgramStudiuService } from './program-studiu.service';

@Component({
  selector: 'jhi-program-studiu-delete-dialog',
  templateUrl: './program-studiu-delete-dialog.component.html'
})
export class ProgramStudiuDeleteDialogComponent {
  programStudiu: IProgramStudiu;

  constructor(
    protected programStudiuService: ProgramStudiuService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.programStudiuService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'programStudiuListModification',
        content: 'Deleted an programStudiu'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-program-studiu-delete-popup',
  template: ''
})
export class ProgramStudiuDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ programStudiu }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProgramStudiuDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.programStudiu = programStudiu;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/program-studiu', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/program-studiu', { outlets: { popup: null } }]);
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
