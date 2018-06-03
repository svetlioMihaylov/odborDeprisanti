import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { VacationRequests } from './vacation-requests.model';
import { VacationRequestsService } from './vacation-requests.service';

@Component({
    selector: 'jhi-vacation-requests-detail',
    templateUrl: './vacation-requests-detail.component.html'
})
export class VacationRequestsDetailComponent implements OnInit, OnDestroy {

    vacationRequests: VacationRequests;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private vacationRequestsService: VacationRequestsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVacationRequests();
    }

    load(id) {
        this.vacationRequestsService.find(id)
            .subscribe((vacationRequestsResponse: HttpResponse<VacationRequests>) => {
                this.vacationRequests = vacationRequestsResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVacationRequests() {
        this.eventSubscriber = this.eventManager.subscribe(
            'vacationRequestsListModification',
            (response) => this.load(this.vacationRequests.id)
        );
    }
}
