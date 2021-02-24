import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICadruDidactic } from 'app/shared/model/cadru-didactic.model';

@Component({
  selector: 'jhi-cadru-didactic-detail',
  templateUrl: './cadru-didactic-detail.component.html'
})
export class CadruDidacticDetailComponent implements OnInit {
  cadruDidactic: ICadruDidactic;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cadruDidactic }) => {
      this.cadruDidactic = cadruDidactic;
    });
  }

  previousState() {
    window.history.back();
  }
}
