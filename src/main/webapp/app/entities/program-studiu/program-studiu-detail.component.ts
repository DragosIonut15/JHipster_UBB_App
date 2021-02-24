import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgramStudiu } from 'app/shared/model/program-studiu.model';

@Component({
  selector: 'jhi-program-studiu-detail',
  templateUrl: './program-studiu-detail.component.html'
})
export class ProgramStudiuDetailComponent implements OnInit {
  programStudiu: IProgramStudiu;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ programStudiu }) => {
      this.programStudiu = programStudiu;
    });
  }

  previousState() {
    window.history.back();
  }
}
