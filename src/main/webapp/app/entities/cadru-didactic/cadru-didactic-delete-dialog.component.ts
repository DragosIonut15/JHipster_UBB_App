import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICadruDidactic } from 'app/shared/model/cadru-didactic.model';
import { CadruDidacticService } from './cadru-didactic.service';

@Component({
  selector: 'jhi-cadru-didactic-delete-dialog',
  templateUrl: './cadru-didactic-delete-dialog.component.html'
})
export class CadruDidacticDeleteDialogComponent {
  cadruDidactic: ICadruDidactic;

  constructor(
    protected cadruDidacticService: CadruDidacticService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cadruDidacticService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cadruDidacticListModification',
        content: 'Deleted an cadruDidactic'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cadru-didactic-delete-popup',
  template: ''
})
export class CadruDidacticDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cadruDidactic }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CadruDidacticDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cadruDidactic = cadruDidactic;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/cadru-didactic', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/cadru-didactic', { outlets: { popup: null } }]);
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
