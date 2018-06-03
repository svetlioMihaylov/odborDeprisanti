import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ContactInformation } from './contact-information.model';
import { ContactInformationPopupService } from './contact-information-popup.service';
import { ContactInformationService } from './contact-information.service';

@Component({
    selector: 'jhi-contact-information-delete-dialog',
    templateUrl: './contact-information-delete-dialog.component.html'
})
export class ContactInformationDeleteDialogComponent {

    contactInformation: ContactInformation;

    constructor(
        private contactInformationService: ContactInformationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contactInformationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'contactInformationListModification',
                content: 'Deleted an contactInformation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contact-information-delete-popup',
    template: ''
})
export class ContactInformationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contactInformationPopupService: ContactInformationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.contactInformationPopupService
                .open(ContactInformationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
