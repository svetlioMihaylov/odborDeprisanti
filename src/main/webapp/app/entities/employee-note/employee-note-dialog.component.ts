import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EmployeeNote } from './employee-note.model';
import { EmployeeNotePopupService } from './employee-note-popup.service';
import { EmployeeNoteService } from './employee-note.service';
import { Employee, EmployeeService } from '../employee';

@Component({
    selector: 'jhi-employee-note-dialog',
    templateUrl: './employee-note-dialog.component.html'
})
export class EmployeeNoteDialogComponent implements OnInit {

    employeeNote: EmployeeNote;
    isSaving: boolean;

    employees: Employee[];
    dateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private employeeNoteService: EmployeeNoteService,
        private employeeService: EmployeeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.employeeService.query()
            .subscribe((res: HttpResponse<Employee[]>) => { this.employees = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.employeeNote.id !== undefined) {
            this.subscribeToSaveResponse(
                this.employeeNoteService.update(this.employeeNote));
        } else {
            this.subscribeToSaveResponse(
                this.employeeNoteService.create(this.employeeNote));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EmployeeNote>>) {
        result.subscribe((res: HttpResponse<EmployeeNote>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EmployeeNote) {
        this.eventManager.broadcast({ name: 'employeeNoteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackEmployeeById(index: number, item: Employee) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-employee-note-popup',
    template: ''
})
export class EmployeeNotePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeNotePopupService: EmployeeNotePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.employeeNotePopupService
                    .open(EmployeeNoteDialogComponent as Component, params['id']);
            } else {
                this.employeeNotePopupService
                    .open(EmployeeNoteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
