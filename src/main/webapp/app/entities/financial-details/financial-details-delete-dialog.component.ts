import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FinancialDetails } from './financial-details.model';
import { FinancialDetailsPopupService } from './financial-details-popup.service';
import { FinancialDetailsService } from './financial-details.service';

@Component({
    selector: 'jhi-financial-details-delete-dialog',
    templateUrl: './financial-details-delete-dialog.component.html'
})
export class FinancialDetailsDeleteDialogComponent {

    financialDetails: FinancialDetails;

    constructor(
        private financialDetailsService: FinancialDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.financialDetailsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'financialDetailsListModification',
                content: 'Deleted an financialDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-financial-details-delete-popup',
    template: ''
})
export class FinancialDetailsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private financialDetailsPopupService: FinancialDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.financialDetailsPopupService
                .open(FinancialDetailsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
