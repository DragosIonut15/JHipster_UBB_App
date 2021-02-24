import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDepartament, Departament } from 'app/shared/model/departament.model';
import { DepartamentService } from './departament.service';

@Component({
  selector: 'jhi-departament-update',
  templateUrl: './departament-update.component.html'
})
export class DepartamentUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    numeDepartament: [null, [Validators.required]]
  });

  constructor(protected departamentService: DepartamentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ departament }) => {
      this.updateForm(departament);
    });
  }

  updateForm(departament: IDepartament) {
    this.editForm.patchValue({
      id: departament.id,
      numeDepartament: departament.numeDepartament
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const departament = this.createFromForm();
    if (departament.id !== undefined) {
      this.subscribeToSaveResponse(this.departamentService.update(departament));
    } else {
      this.subscribeToSaveResponse(this.departamentService.create(departament));
    }
  }

  private createFromForm(): IDepartament {
    return {
      ...new Departament(),
      id: this.editForm.get(['id']).value,
      numeDepartament: this.editForm.get(['numeDepartament']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartament>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
