import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDCard } from './id-card.model';
import { IDCardPopupService } from './id-card-popup.service';
import { IDCardService } from './id-card.service';

@Component({
    selector: 'jhi-id-card-delete-dialog',
    templateUrl: './id-card-delete-dialog.component.html'
})
export class IDCardDeleteDialogComponent {

    iDCard: IDCard;

    constructor(
        private iDCardService: IDCardService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.iDCardService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'iDCardListModification',
                content: 'Deleted an iDCard'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-id-card-delete-popup',
    template: ''
})
export class IDCardDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private iDCardPopupService: IDCardPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.iDCardPopupService
                .open(IDCardDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
