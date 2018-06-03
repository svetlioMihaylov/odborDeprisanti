import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ContactInformation } from './contact-information.model';
import { ContactInformationService } from './contact-information.service';

@Component({
    selector: 'jhi-contact-information-detail',
    templateUrl: './contact-information-detail.component.html'
})
export class ContactInformationDetailComponent implements OnInit, OnDestroy {

    contactInformation: ContactInformation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private contactInformationService: ContactInformationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInContactInformations();
    }

    load(id) {
        this.contactInformationService.find(id)
            .subscribe((contactInformationResponse: HttpResponse<ContactInformation>) => {
                this.contactInformation = contactInformationResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInContactInformations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'contactInformationListModification',
            (response) => this.load(this.contactInformation.id)
        );
    }
}
