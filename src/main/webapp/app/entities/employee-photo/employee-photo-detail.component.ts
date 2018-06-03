import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { EmployeePhoto } from './employee-photo.model';
import { EmployeePhotoService } from './employee-photo.service';

@Component({
    selector: 'jhi-employee-photo-detail',
    templateUrl: './employee-photo-detail.component.html'
})
export class EmployeePhotoDetailComponent implements OnInit, OnDestroy {

    employeePhoto: EmployeePhoto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private employeePhotoService: EmployeePhotoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmployeePhotos();
    }

    load(id) {
        this.employeePhotoService.find(id)
            .subscribe((employeePhotoResponse: HttpResponse<EmployeePhoto>) => {
                this.employeePhoto = employeePhotoResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmployeePhotos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'employeePhotoListModification',
            (response) => this.load(this.employeePhoto.id)
        );
    }
}
