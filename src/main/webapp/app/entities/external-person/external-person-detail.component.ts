import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ExternalPerson } from './external-person.model';
import { ExternalPersonService } from './external-person.service';

@Component({
    selector: 'jhi-external-person-detail',
    templateUrl: './external-person-detail.component.html'
})
export class ExternalPersonDetailComponent implements OnInit, OnDestroy {

    externalPerson: ExternalPerson;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private externalPersonService: ExternalPersonService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInExternalPeople();
    }

    load(id) {
        this.externalPersonService.find(id)
            .subscribe((externalPersonResponse: HttpResponse<ExternalPerson>) => {
                this.externalPerson = externalPersonResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInExternalPeople() {
        this.eventSubscriber = this.eventManager.subscribe(
            'externalPersonListModification',
            (response) => this.load(this.externalPerson.id)
        );
    }
}
