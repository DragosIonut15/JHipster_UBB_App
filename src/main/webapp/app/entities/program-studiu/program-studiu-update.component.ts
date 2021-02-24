import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProgramStudiu, ProgramStudiu } from 'app/shared/model/program-studiu.model';
import { ProgramStudiuService } from './program-studiu.service';
import { IDepartament } from 'app/shared/model/departament.model';
import { DepartamentService } from 'app/entities/departament';

@Component({
  selector: 'jhi-program-studiu-update',
  templateUrl: './program-studiu-update.component.html'
})
export class ProgramStudiuUpdateComponent implements OnInit {
  isSaving: boolean;

  departaments: IDepartament[];

  editForm = this.fb.group({
    id: [],
    program: [],
    departament: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected programStudiuService: ProgramStudiuService,
    protected departamentService: DepartamentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ programStudiu }) => {
      this.updateForm(programStudiu);
    });
    this.departamentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDepartament[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDepartament[]>) => response.body)
      )
      .subscribe((res: IDepartament[]) => (this.departaments = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(programStudiu: IProgramStudiu) {
    this.editForm.patchValue({
      id: programStudiu.id,
      program: programStudiu.program,
      departament: programStudiu.departament
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const programStudiu = this.createFromForm();
    if (programStudiu.id !== undefined) {
      this.subscribeToSaveResponse(this.programStudiuService.update(programStudiu));
    } else {
      this.subscribeToSaveResponse(this.programStudiuService.create(programStudiu));
    }
  }

  private createFromForm(): IProgramStudiu {
    return {
      ...new ProgramStudiu(),
      id: this.editForm.get(['id']).value,
      program: this.editForm.get(['program']).value,
      departament: this.editForm.get(['departament']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgramStudiu>>) {
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
