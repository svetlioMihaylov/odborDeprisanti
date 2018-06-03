import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { FinancialDetails } from './financial-details.model';
import { FinancialDetailsService } from './financial-details.service';

@Component({
    selector: 'jhi-financial-details-detail',
    templateUrl: './financial-details-detail.component.html'
})
export class FinancialDetailsDetailComponent implements OnInit, OnDestroy {

    financialDetails: FinancialDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private financialDetailsService: FinancialDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFinancialDetails();
    }

    load(id) {
        this.financialDetailsService.find(id)
            .subscribe((financialDetailsResponse: HttpResponse<FinancialDetails>) => {
                this.financialDetails = financialDetailsResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFinancialDetails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'financialDetailsListModification',
            (response) => this.load(this.financialDetails.id)
        );
    }
}
