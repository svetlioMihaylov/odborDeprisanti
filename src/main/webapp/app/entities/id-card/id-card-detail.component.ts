import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { IDCard } from './id-card.model';
import { IDCardService } from './id-card.service';

@Component({
    selector: 'jhi-id-card-detail',
    templateUrl: './id-card-detail.component.html'
})
export class IDCardDetailComponent implements OnInit, OnDestroy {

    iDCard: IDCard;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private iDCardService: IDCardService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIDCards();
    }

    load(id) {
        this.iDCardService.find(id)
            .subscribe((iDCardResponse: HttpResponse<IDCard>) => {
                this.iDCard = iDCardResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIDCards() {
        this.eventSubscriber = this.eventManager.subscribe(
            'iDCardListModification',
            (response) => this.load(this.iDCard.id)
        );
    }
}
