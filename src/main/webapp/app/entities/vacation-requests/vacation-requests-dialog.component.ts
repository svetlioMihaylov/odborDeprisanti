import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VacationRequests } from './vacation-requests.model';
import { VacationRequestsPopupService } from './vacation-requests-popup.service';
import { VacationRequestsService } from './vacation-requests.service';
import { Employee, EmployeeService } from '../employee';

@Component({
    selector: 'jhi-vacation-requests-dialog',
    templateUrl: './vacation-requests-dialog.component.html'
})
export class VacationRequestsDialogComponent implements OnInit {

    vacationRequests: VacationRequests;
    isSaving: boolean;

    employees: Employee[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private vacationRequestsService: VacationRequestsService,
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
        if (this.vacationRequests.id !== undefined) {
            this.subscribeToSaveResponse(
                this.vacationRequestsService.update(this.vacationRequests));
        } else {
            this.subscribeToSaveResponse(
                this.vacationRequestsService.create(this.vacationRequests));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<VacationRequests>>) {
        result.subscribe((res: HttpResponse<VacationRequests>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: VacationRequests) {
        this.eventManager.broadcast({ name: 'vacationRequestsListModification', content: 'OK'});
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
    selector: 'jhi-vacation-requests-popup',
    template: ''
})
export class VacationRequestsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vacationRequestsPopupService: VacationRequestsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.vacationRequestsPopupService
                    .open(VacationRequestsDialogComponent as Component, params['id']);
            } else {
                this.vacationRequestsPopupService
                    .open(VacationRequestsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
