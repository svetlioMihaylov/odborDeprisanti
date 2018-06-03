import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { VacationRequests } from './vacation-requests.model';
import { VacationRequestsService } from './vacation-requests.service';

@Injectable()
export class VacationRequestsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private vacationRequestsService: VacationRequestsService

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
                this.vacationRequestsService.find(id)
                    .subscribe((vacationRequestsResponse: HttpResponse<VacationRequests>) => {
                        const vacationRequests: VacationRequests = vacationRequestsResponse.body;
                        this.ngbModalRef = this.vacationRequestsModalRef(component, vacationRequests);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.vacationRequestsModalRef(component, new VacationRequests());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    vacationRequestsModalRef(component: Component, vacationRequests: VacationRequests): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.vacationRequests = vacationRequests;
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
