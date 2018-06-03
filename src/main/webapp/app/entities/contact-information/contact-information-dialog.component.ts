import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ContactInformation } from './contact-information.model';
import { ContactInformationPopupService } from './contact-information-popup.service';
import { ContactInformationService } from './contact-information.service';

@Component({
    selector: 'jhi-contact-information-dialog',
    templateUrl: './contact-information-dialog.component.html'
})
export class ContactInformationDialogComponent implements OnInit {

    contactInformation: ContactInformation;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private contactInformationService: ContactInformationService,
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
        if (this.contactInformation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.contactInformationService.update(this.contactInformation));
        } else {
            this.subscribeToSaveResponse(
                this.contactInformationService.create(this.contactInformation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ContactInformation>>) {
        result.subscribe((res: HttpResponse<ContactInformation>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ContactInformation) {
        this.eventManager.broadcast({ name: 'contactInformationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-contact-information-popup',
    template: ''
})
export class ContactInformationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contactInformationPopupService: ContactInformationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.contactInformationPopupService
                    .open(ContactInformationDialogComponent as Component, params['id']);
            } else {
                this.contactInformationPopupService
                    .open(ContactInformationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
