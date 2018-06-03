import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Benefit } from './benefit.model';
import { BenefitService } from './benefit.service';

@Component({
    selector: 'jhi-benefit-detail',
    templateUrl: './benefit-detail.component.html'
})
export class BenefitDetailComponent implements OnInit, OnDestroy {

    benefit: Benefit;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private benefitService: BenefitService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBenefits();
    }

    load(id) {
        this.benefitService.find(id)
            .subscribe((benefitResponse: HttpResponse<Benefit>) => {
                this.benefit = benefitResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBenefits() {
        this.eventSubscriber = this.eventManager.subscribe(
            'benefitListModification',
            (response) => this.load(this.benefit.id)
        );
    }
}
