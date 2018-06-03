import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FinancialDetails } from './financial-details.model';
import { FinancialDetailsPopupService } from './financial-details-popup.service';
import { FinancialDetailsService } from './financial-details.service';

@Component({
    selector: 'jhi-financial-details-dialog',
    templateUrl: './financial-details-dialog.component.html'
})
export class FinancialDetailsDialogComponent implements OnInit {

    financialDetails: FinancialDetails;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private financialDetailsService: FinancialDetailsService,
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
        if (this.financialDetails.id !== undefined) {
            this.subscribeToSaveResponse(
                this.financialDetailsService.update(this.financialDetails));
        } else {
            this.subscribeToSaveResponse(
                this.financialDetailsService.create(this.financialDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<FinancialDetails>>) {
        result.subscribe((res: HttpResponse<FinancialDetails>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: FinancialDetails) {
        this.eventManager.broadcast({ name: 'financialDetailsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-financial-details-popup',
    template: ''
})
export class FinancialDetailsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private financialDetailsPopupService: FinancialDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.financialDetailsPopupService
                    .open(FinancialDetailsDialogComponent as Component, params['id']);
            } else {
                this.financialDetailsPopupService
                    .open(FinancialDetailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
