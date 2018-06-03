import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeePossition } from './employee-possition.model';
import { EmployeePossitionPopupService } from './employee-possition-popup.service';
import { EmployeePossitionService } from './employee-possition.service';

@Component({
    selector: 'jhi-employee-possition-dialog',
    templateUrl: './employee-possition-dialog.component.html'
})
export class EmployeePossitionDialogComponent implements OnInit {

    employeePossition: EmployeePossition;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private employeePossitionService: EmployeePossitionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.employeePossition.id !== undefined) {
            this.subscribeToSaveResponse(
                this.employeePossitionService.update(this.employeePossition));
        } else {
            this.subscribeToSaveResponse(
                this.employeePossitionService.create(this.employeePossition));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EmployeePossition>>) {
        result.subscribe((res: HttpResponse<EmployeePossition>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EmployeePossition) {
        this.eventManager.broadcast({ name: 'employeePossitionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-employee-possition-popup',
    template: ''
})
export class EmployeePossitionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeePossitionPopupService: EmployeePossitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.employeePossitionPopupService
                    .open(EmployeePossitionDialogComponent as Component, params['id']);
            } else {
                this.employeePossitionPopupService
                    .open(EmployeePossitionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
