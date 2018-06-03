import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ExternalPerson } from './external-person.model';
import { ExternalPersonPopupService } from './external-person-popup.service';
import { ExternalPersonService } from './external-person.service';

@Component({
    selector: 'jhi-external-person-delete-dialog',
    templateUrl: './external-person-delete-dialog.component.html'
})
export class ExternalPersonDeleteDialogComponent {

    externalPerson: ExternalPerson;

    constructor(
        private externalPersonService: ExternalPersonService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.externalPersonService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'externalPersonListModification',
                content: 'Deleted an externalPerson'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-external-person-delete-popup',
    template: ''
})
export class ExternalPersonDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private externalPersonPopupService: ExternalPersonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.externalPersonPopupService
                .open(ExternalPersonDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
