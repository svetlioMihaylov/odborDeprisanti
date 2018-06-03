import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ExternalPerson } from './external-person.model';
import { ExternalPersonPopupService } from './external-person-popup.service';
import { ExternalPersonService } from './external-person.service';
import { Employee, EmployeeService } from '../employee';

@Component({
    selector: 'jhi-external-person-dialog',
    templateUrl: './external-person-dialog.component.html'
})
export class ExternalPersonDialogComponent implements OnInit {

    externalPerson: ExternalPerson;
    isSaving: boolean;

    employees: Employee[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private externalPersonService: ExternalPersonService,
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
        if (this.externalPerson.id !== undefined) {
            this.subscribeToSaveResponse(
                this.externalPersonService.update(this.externalPerson));
        } else {
            this.subscribeToSaveResponse(
                this.externalPersonService.create(this.externalPerson));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ExternalPerson>>) {
        result.subscribe((res: HttpResponse<ExternalPerson>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ExternalPerson) {
        this.eventManager.broadcast({ name: 'externalPersonListModification', content: 'OK'});
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
    selector: 'jhi-external-person-popup',
    template: ''
})
export class ExternalPersonPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private externalPersonPopupService: ExternalPersonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.externalPersonPopupService
                    .open(ExternalPersonDialogComponent as Component, params['id']);
            } else {
                this.externalPersonPopupService
                    .open(ExternalPersonDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
