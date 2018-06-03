import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VacationRequests } from './vacation-requests.model';
import { VacationRequestsPopupService } from './vacation-requests-popup.service';
import { VacationRequestsService } from './vacation-requests.service';

@Component({
    selector: 'jhi-vacation-requests-delete-dialog',
    templateUrl: './vacation-requests-delete-dialog.component.html'
})
export class VacationRequestsDeleteDialogComponent {

    vacationRequests: VacationRequests;

    constructor(
        private vacationRequestsService: VacationRequestsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vacationRequestsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'vacationRequestsListModification',
                content: 'Deleted an vacationRequests'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vacation-requests-delete-popup',
    template: ''
})
export class VacationRequestsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vacationRequestsPopupService: VacationRequestsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.vacationRequestsPopupService
                .open(VacationRequestsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
