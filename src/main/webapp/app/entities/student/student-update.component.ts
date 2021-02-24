import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IStudent, Student } from 'app/shared/model/student.model';
import { StudentService } from './student.service';
import { ICadruDidactic } from 'app/shared/model/cadru-didactic.model';
import { CadruDidacticService } from 'app/entities/cadru-didactic';

@Component({
  selector: 'jhi-student-update',
  templateUrl: './student-update.component.html'
})
export class StudentUpdateComponent implements OnInit {
  isSaving: boolean;

  cadrudidactics: ICadruDidactic[];

  editForm = this.fb.group({
    id: [],
    nume: [null, [Validators.required]],
    prenume: [null, [Validators.required]],
    numarMatricol: [],
    lucrareLicenta: [],
    linia: [],
    forma: [],
    cadruDidactic: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected studentService: StudentService,
    protected cadruDidacticService: CadruDidacticService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ student }) => {
      this.updateForm(student);
    });
    this.cadruDidacticService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICadruDidactic[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICadruDidactic[]>) => response.body)
      )
      .subscribe((res: ICadruDidactic[]) => (this.cadrudidactics = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(student: IStudent) {
    this.editForm.patchValue({
      id: student.id,
      nume: student.nume,
      prenume: student.prenume,
      numarMatricol: student.numarMatricol,
      lucrareLicenta: student.lucrareLicenta,
      linia: student.linia,
      forma: student.forma,
      cadruDidactic: student.cadruDidactic
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const student = this.createFromForm();
    if (student.id !== undefined) {
      this.subscribeToSaveResponse(this.studentService.update(student));
    } else {
      this.subscribeToSaveResponse(this.studentService.create(student));
    }
  }

  private createFromForm(): IStudent {
    return {
      ...new Student(),
      id: this.editForm.get(['id']).value,
      nume: this.editForm.get(['nume']).value,
      prenume: this.editForm.get(['prenume']).value,
      numarMatricol: this.editForm.get(['numarMatricol']).value,
      lucrareLicenta: this.editForm.get(['lucrareLicenta']).value,
      linia: this.editForm.get(['linia']).value,
      forma: this.editForm.get(['forma']).value,
      cadruDidactic: this.editForm.get(['cadruDidactic']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>) {
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

  trackCadruDidacticById(index: number, item: ICadruDidactic) {
    return item.id;
  }
}
