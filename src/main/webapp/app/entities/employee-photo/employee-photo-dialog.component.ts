import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { EmployeePhoto } from './employee-photo.model';
import { EmployeePhotoPopupService } from './employee-photo-popup.service';
import { EmployeePhotoService } from './employee-photo.service';

@Component({
    selector: 'jhi-employee-photo-dialog',
    templateUrl: './employee-photo-dialog.component.html'
})
export class EmployeePhotoDialogComponent implements OnInit {

    employeePhoto: EmployeePhoto;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private employeePhotoService: EmployeePhotoService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.employeePhoto, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.employeePhoto.id !== undefined) {
            this.subscribeToSaveResponse(
                this.employeePhotoService.update(this.employeePhoto));
        } else {
            this.subscribeToSaveResponse(
                this.employeePhotoService.create(this.employeePhoto));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EmployeePhoto>>) {
        result.subscribe((res: HttpResponse<EmployeePhoto>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EmployeePhoto) {
        this.eventManager.broadcast({ name: 'employeePhotoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-employee-photo-popup',
    template: ''
})
export class EmployeePhotoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeePhotoPopupService: EmployeePhotoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.employeePhotoPopupService
                    .open(EmployeePhotoDialogComponent as Component, params['id']);
            } else {
                this.employeePhotoPopupService
                    .open(EmployeePhotoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
