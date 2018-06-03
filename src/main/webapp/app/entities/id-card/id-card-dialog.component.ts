import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDCard } from './id-card.model';
import { IDCardPopupService } from './id-card-popup.service';
import { IDCardService } from './id-card.service';

@Component({
    selector: 'jhi-id-card-dialog',
    templateUrl: './id-card-dialog.component.html'
})
export class IDCardDialogComponent implements OnInit {

    iDCard: IDCard;
    isSaving: boolean;
    dateOfIssueDp: any;
    dateOfExpiryDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private iDCardService: IDCardService,
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
        if (this.iDCard.id !== undefined) {
            this.subscribeToSaveResponse(
                this.iDCardService.update(this.iDCard));
        } else {
            this.subscribeToSaveResponse(
                this.iDCardService.create(this.iDCard));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDCard>>) {
        result.subscribe((res: HttpResponse<IDCard>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: IDCard) {
        this.eventManager.broadcast({ name: 'iDCardListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-id-card-popup',
    template: ''
})
export class IDCardPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private iDCardPopupService: IDCardPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.iDCardPopupService
                    .open(IDCardDialogComponent as Component, params['id']);
            } else {
                this.iDCardPopupService
                    .open(IDCardDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
