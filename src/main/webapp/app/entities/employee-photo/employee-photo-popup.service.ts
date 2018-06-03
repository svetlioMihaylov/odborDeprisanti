import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { EmployeePhoto } from './employee-photo.model';
import { EmployeePhotoService } from './employee-photo.service';

@Injectable()
export class EmployeePhotoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private employeePhotoService: EmployeePhotoService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.employeePhotoService.find(id)
                    .subscribe((employeePhotoResponse: HttpResponse<EmployeePhoto>) => {
                        const employeePhoto: EmployeePhoto = employeePhotoResponse.body;
                        this.ngbModalRef = this.employeePhotoModalRef(component, employeePhoto);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.employeePhotoModalRef(component, new EmployeePhoto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    employeePhotoModalRef(component: Component, employeePhoto: EmployeePhoto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.employeePhoto = employeePhoto;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
