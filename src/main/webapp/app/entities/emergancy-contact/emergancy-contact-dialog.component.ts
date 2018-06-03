import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmergancyContact } from './emergancy-contact.model';
import { EmergancyContactPopupService } from './emergancy-contact-popup.service';
import { EmergancyContactService } from './emergancy-contact.service';

@Component({
    selector: 'jhi-emergancy-contact-dialog',
    templateUrl: './emergancy-contact-dialog.component.html'
})
export class EmergancyContactDialogComponent implements OnInit {

    emergancyContact: EmergancyContact;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private emergancyContactService: EmergancyContactService,
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
        if (this.emergancyContact.id !== undefined) {
            this.subscribeToSaveResponse(
                this.emergancyContactService.update(this.emergancyContact));
        } else {
            this.subscribeToSaveResponse(
                this.emergancyContactService.create(this.emergancyContact));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EmergancyContact>>) {
        result.subscribe((res: HttpResponse<EmergancyContact>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EmergancyContact) {
        this.eventManager.broadcast({ name: 'emergancyContactListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-emergancy-contact-popup',
    template: ''
})
export class EmergancyContactPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private emergancyContactPopupService: EmergancyContactPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.emergancyContactPopupService
                    .open(EmergancyContactDialogComponent as Component, params['id']);
            } else {
                this.emergancyContactPopupService
                    .open(EmergancyContactDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
