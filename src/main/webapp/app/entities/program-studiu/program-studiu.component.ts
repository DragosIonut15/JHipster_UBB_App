import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProgramStudiu } from 'app/shared/model/program-studiu.model';
import { AccountService } from 'app/core';
import { ProgramStudiuService } from './program-studiu.service';

@Component({
  selector: 'jhi-program-studiu',
  templateUrl: './program-studiu.component.html'
})
export class ProgramStudiuComponent implements OnInit, OnDestroy {
  programStudius: IProgramStudiu[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected programStudiuService: ProgramStudiuService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.programStudiuService
      .query()
      .pipe(
        filter((res: HttpResponse<IProgramStudiu[]>) => res.ok),
        map((res: HttpResponse<IProgramStudiu[]>) => res.body)
      )
      .subscribe(
        (res: IProgramStudiu[]) => {
          this.programStudius = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProgramStudius();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProgramStudiu) {
    return item.id;
  }

  registerChangeInProgramStudius() {
    this.eventSubscriber = this.eventManager.subscribe('programStudiuListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
