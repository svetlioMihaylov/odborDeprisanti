import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Benefit } from './benefit.model';
import { BenefitPopupService } from './benefit-popup.service';
import { BenefitService } from './benefit.service';
import { Employee, EmployeeService } from '../employee';
import { ExternalPerson, ExternalPersonService } from '../external-person';

@Component({
    selector: 'jhi-benefit-dialog',
    templateUrl: './benefit-dialog.component.html'
})
export class BenefitDialogComponent implements OnInit {

    benefit: Benefit;
    isSaving: boolean;

    employees: Employee[];

    externalpeople: ExternalPerson[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private benefitService: BenefitService,
        private employeeService: EmployeeService,
        private externalPersonService: ExternalPersonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.employeeService.query()
            .subscribe((res: HttpResponse<Employee[]>) => { this.employees = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.externalPersonService.query()
            .subscribe((res: HttpResponse<ExternalPerson[]>) => { this.externalpeople = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.benefit.id !== undefined) {
            this.subscribeToSaveResponse(
                this.benefitService.update(this.benefit));
        } else {
            this.subscribeToSaveResponse(
                this.benefitService.create(this.benefit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Benefit>>) {
        result.subscribe((res: HttpResponse<Benefit>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Benefit) {
        this.eventManager.broadcast({ name: 'benefitListModification', content: 'OK'});
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

    trackExternalPersonById(index: number, item: ExternalPerson) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-benefit-popup',
    template: ''
})
export class BenefitPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private benefitPopupService: BenefitPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.benefitPopupService
                    .open(BenefitDialogComponent as Component, params['id']);
            } else {
                this.benefitPopupService
                    .open(BenefitDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
