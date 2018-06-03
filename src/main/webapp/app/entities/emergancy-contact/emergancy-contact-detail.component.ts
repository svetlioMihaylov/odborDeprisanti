import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EmergancyContact } from './emergancy-contact.model';
import { EmergancyContactService } from './emergancy-contact.service';

@Component({
    selector: 'jhi-emergancy-contact-detail',
    templateUrl: './emergancy-contact-detail.component.html'
})
export class EmergancyContactDetailComponent implements OnInit, OnDestroy {

    emergancyContact: EmergancyContact;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private emergancyContactService: EmergancyContactService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmergancyContacts();
    }

    load(id) {
        this.emergancyContactService.find(id)
            .subscribe((emergancyContactResponse: HttpResponse<EmergancyContact>) => {
                this.emergancyContact = emergancyContactResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmergancyContacts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'emergancyContactListModification',
            (response) => this.load(this.emergancyContact.id)
        );
    }
}
