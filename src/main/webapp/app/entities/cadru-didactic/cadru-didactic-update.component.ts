import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICadruDidactic, CadruDidactic } from 'app/shared/model/cadru-didactic.model';
import { CadruDidacticService } from './cadru-didactic.service';
import { IDepartament } from 'app/shared/model/departament.model';
import { DepartamentService } from 'app/entities/departament';

@Component({
  selector: 'jhi-cadru-didactic-update',
  templateUrl: './cadru-didactic-update.component.html'
})
export class CadruDidacticUpdateComponent implements OnInit {
  isSaving: boolean;

  departaments: IDepartament[];

  editForm = this.fb.group({
    id: [],
    nume: [null, [Validators.required]],
    prenume: [null, [Validators.required]],
    titlu: [null, [Validators.required]],
    email: [],
    birou: [],
    departament: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cadruDidacticService: CadruDidacticService,
    protected departamentService: DepartamentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cadruDidactic }) => {
      this.updateForm(cadruDidactic);
    });
    this.departamentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDepartament[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDepartament[]>) => response.body)
      )
      .subscribe((res: IDepartament[]) => (this.departaments = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cadruDidactic: ICadruDidactic) {
    this.editForm.patchValue({
      id: cadruDidactic.id,
      nume: cadruDidactic.nume,
      prenume: cadruDidactic.prenume,
      titlu: cadruDidactic.titlu,
      email: cadruDidactic.email,
      birou: cadruDidactic.birou,
      departament: cadruDidactic.departament
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cadruDidactic = this.createFromForm();
    if (cadruDidactic.id !== undefined) {
      this.subscribeToSaveResponse(this.cadruDidacticService.update(cadruDidactic));
    } else {
      this.subscribeToSaveResponse(this.cadruDidacticService.create(cadruDidactic));
    }
  }

  private createFromForm(): ICadruDidactic {
    return {
      ...new CadruDidactic(),
      id: this.editForm.get(['id']).value,
      nume: this.editForm.get(['nume']).value,
      prenume: this.editForm.get(['prenume']).value,
      titlu: this.editForm.get(['titlu']).value,
      email: this.editForm.get(['email']).value,
      birou: this.editForm.get(['birou']).value,
      departament: this.editForm.get(['departament']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICadruDidactic>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackDepartamentById(index: number, item: IDepartament) {
    return item.id;
  }
}
