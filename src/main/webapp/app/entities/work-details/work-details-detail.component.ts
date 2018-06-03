import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { WorkDetails } from './work-details.model';
import { WorkDetailsService } from './work-details.service';

@Component({
    selector: 'jhi-work-details-detail',
    templateUrl: './work-details-detail.component.html'
})
export class WorkDetailsDetailComponent implements OnInit, OnDestroy {

    workDetails: WorkDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private workDetailsService: WorkDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWorkDetails();
    }

    load(id) {
        this.workDetailsService.find(id)
            .subscribe((workDetailsResponse: HttpResponse<WorkDetails>) => {
                this.workDetails = workDetailsResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWorkDetails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'workDetailsListModification',
            (response) => this.load(this.workDetails.id)
        );
    }
}
