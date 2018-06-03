import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmergancyContact } from './emergancy-contact.model';
import { EmergancyContactPopupService } from './emergancy-contact-popup.service';
import { EmergancyContactService } from './emergancy-contact.service';

@Component({
    selector: 'jhi-emergancy-contact-delete-dialog',
    templateUrl: './emergancy-contact-delete-dialog.component.html'
})
export class EmergancyContactDeleteDialogComponent {

    emergancyContact: EmergancyContact;

    constructor(
        private emergancyContactService: EmergancyContactService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.emergancyContactService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'emergancyContactListModification',
                content: 'Deleted an emergancyContact'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-emergancy-contact-delete-popup',
    template: ''
})
export class EmergancyContactDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private emergancyContactPopupService: EmergancyContactPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.emergancyContactPopupService
                .open(EmergancyContactDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
