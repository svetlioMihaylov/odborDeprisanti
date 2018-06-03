import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WorkDetails } from './work-details.model';
import { WorkDetailsPopupService } from './work-details-popup.service';
import { WorkDetailsService } from './work-details.service';

@Component({
    selector: 'jhi-work-details-delete-dialog',
    templateUrl: './work-details-delete-dialog.component.html'
})
export class WorkDetailsDeleteDialogComponent {

    workDetails: WorkDetails;

    constructor(
        private workDetailsService: WorkDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.workDetailsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'workDetailsListModification',
                content: 'Deleted an workDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-work-details-delete-popup',
    template: ''
})
export class WorkDetailsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private workDetailsPopupService: WorkDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.workDetailsPopupService
                .open(WorkDetailsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
