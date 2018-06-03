import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeePossition } from './employee-possition.model';
import { EmployeePossitionPopupService } from './employee-possition-popup.service';
import { EmployeePossitionService } from './employee-possition.service';

@Component({
    selector: 'jhi-employee-possition-delete-dialog',
    templateUrl: './employee-possition-delete-dialog.component.html'
})
export class EmployeePossitionDeleteDialogComponent {

    employeePossition: EmployeePossition;

    constructor(
        private employeePossitionService: EmployeePossitionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employeePossitionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'employeePossitionListModification',
                content: 'Deleted an employeePossition'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employee-possition-delete-popup',
    template: ''
})
export class EmployeePossitionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeePossitionPopupService: EmployeePossitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.employeePossitionPopupService
                .open(EmployeePossitionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
