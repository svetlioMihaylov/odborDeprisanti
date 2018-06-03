import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WorkDetails } from './work-details.model';
import { WorkDetailsPopupService } from './work-details-popup.service';
import { WorkDetailsService } from './work-details.service';
import { EmployeePossition, EmployeePossitionService } from '../employee-possition';

@Component({
    selector: 'jhi-work-details-dialog',
    templateUrl: './work-details-dialog.component.html'
})
export class WorkDetailsDialogComponent implements OnInit {

    workDetails: WorkDetails;
    isSaving: boolean;

    possitions: EmployeePossition[];
    startDateDp: any;
    endDateDp: any;
    endOfProbationDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private workDetailsService: WorkDetailsService,
        private employeePossitionService: EmployeePossitionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.employeePossitionService
            .query({filter: 'workdetails-is-null'})
            .subscribe((res: HttpResponse<EmployeePossition[]>) => {
                if (!this.workDetails.possition || !this.workDetails.possition.id) {
                    this.possitions = res.body;
                } else {
                    this.employeePossitionService
                        .find(this.workDetails.possition.id)
                        .subscribe((subRes: HttpResponse<EmployeePossition>) => {
                            this.possitions = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.workDetails.id !== undefined) {
            this.subscribeToSaveResponse(
                this.workDetailsService.update(this.workDetails));
        } else {
            this.subscribeToSaveResponse(
                this.workDetailsService.create(this.workDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<WorkDetails>>) {
        result.subscribe((res: HttpResponse<WorkDetails>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: WorkDetails) {
        this.eventManager.broadcast({ name: 'workDetailsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackEmployeePossitionById(index: number, item: EmployeePossition) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-work-details-popup',
    template: ''
})
export class WorkDetailsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private workDetailsPopupService: WorkDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.workDetailsPopupService
                    .open(WorkDetailsDialogComponent as Component, params['id']);
            } else {
                this.workDetailsPopupService
                    .open(WorkDetailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
