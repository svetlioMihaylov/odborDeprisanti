import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeePhoto } from './employee-photo.model';
import { EmployeePhotoPopupService } from './employee-photo-popup.service';
import { EmployeePhotoService } from './employee-photo.service';

@Component({
    selector: 'jhi-employee-photo-delete-dialog',
    templateUrl: './employee-photo-delete-dialog.component.html'
})
export class EmployeePhotoDeleteDialogComponent {

    employeePhoto: EmployeePhoto;

    constructor(
        private employeePhotoService: EmployeePhotoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employeePhotoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'employeePhotoListModification',
                content: 'Deleted an employeePhoto'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employee-photo-delete-popup',
    template: ''
})
export class EmployeePhotoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeePhotoPopupService: EmployeePhotoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.employeePhotoPopupService
                .open(EmployeePhotoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
