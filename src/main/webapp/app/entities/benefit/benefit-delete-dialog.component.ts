import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Benefit } from './benefit.model';
import { BenefitPopupService } from './benefit-popup.service';
import { BenefitService } from './benefit.service';

@Component({
    selector: 'jhi-benefit-delete-dialog',
    templateUrl: './benefit-delete-dialog.component.html'
})
export class BenefitDeleteDialogComponent {

    benefit: Benefit;

    constructor(
        private benefitService: BenefitService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.benefitService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'benefitListModification',
                content: 'Deleted an benefit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-benefit-delete-popup',
    template: ''
})
export class BenefitDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private benefitPopupService: BenefitPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.benefitPopupService
                .open(BenefitDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
