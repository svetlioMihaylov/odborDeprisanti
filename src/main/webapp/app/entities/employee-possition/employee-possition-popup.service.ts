import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { EmployeePossition } from './employee-possition.model';
import { EmployeePossitionService } from './employee-possition.service';

@Injectable()
export class EmployeePossitionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private employeePossitionService: EmployeePossitionService

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
                this.employeePossitionService.find(id)
                    .subscribe((employeePossitionResponse: HttpResponse<EmployeePossition>) => {
                        const employeePossition: EmployeePossition = employeePossitionResponse.body;
                        this.ngbModalRef = this.employeePossitionModalRef(component, employeePossition);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.employeePossitionModalRef(component, new EmployeePossition());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    employeePossitionModalRef(component: Component, employeePossition: EmployeePossition): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.employeePossition = employeePossition;
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
