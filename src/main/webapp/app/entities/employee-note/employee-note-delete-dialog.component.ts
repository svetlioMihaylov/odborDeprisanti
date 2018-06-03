import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeNote } from './employee-note.model';
import { EmployeeNotePopupService } from './employee-note-popup.service';
import { EmployeeNoteService } from './employee-note.service';

@Component({
    selector: 'jhi-employee-note-delete-dialog',
    templateUrl: './employee-note-delete-dialog.component.html'
})
export class EmployeeNoteDeleteDialogComponent {

    employeeNote: EmployeeNote;

    constructor(
        private employeeNoteService: EmployeeNoteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employeeNoteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'employeeNoteListModification',
                content: 'Deleted an employeeNote'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employee-note-delete-popup',
    template: ''
})
export class EmployeeNoteDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeNotePopupService: EmployeeNotePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.employeeNotePopupService
                .open(EmployeeNoteDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
