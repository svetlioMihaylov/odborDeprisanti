import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeePossition } from './employee-possition.model';
import { EmployeePossitionService } from './employee-possition.service';

@Component({
    selector: 'jhi-employee-possition-detail',
    templateUrl: './employee-possition-detail.component.html'
})
export class EmployeePossitionDetailComponent implements OnInit, OnDestroy {

    employeePossition: EmployeePossition;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private employeePossitionService: EmployeePossitionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmployeePossitions();
    }

    load(id) {
        this.employeePossitionService.find(id)
            .subscribe((employeePossitionResponse: HttpResponse<EmployeePossition>) => {
                this.employeePossition = employeePossitionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmployeePossitions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'employeePossitionListModification',
            (response) => this.load(this.employeePossition.id)
        );
    }
}
