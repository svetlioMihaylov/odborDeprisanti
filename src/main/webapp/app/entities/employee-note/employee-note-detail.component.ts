import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeNote } from './employee-note.model';
import { EmployeeNoteService } from './employee-note.service';

@Component({
    selector: 'jhi-employee-note-detail',
    templateUrl: './employee-note-detail.component.html'
})
export class EmployeeNoteDetailComponent implements OnInit, OnDestroy {

    employeeNote: EmployeeNote;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private employeeNoteService: EmployeeNoteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmployeeNotes();
    }

    load(id) {
        this.employeeNoteService.find(id)
            .subscribe((employeeNoteResponse: HttpResponse<EmployeeNote>) => {
                this.employeeNote = employeeNoteResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmployeeNotes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'employeeNoteListModification',
            (response) => this.load(this.employeeNote.id)
        );
    }
}
