import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { FinancialDetails } from './financial-details.model';
import { FinancialDetailsService } from './financial-details.service';

@Injectable()
export class FinancialDetailsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private financialDetailsService: FinancialDetailsService

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
                this.financialDetailsService.find(id)
                    .subscribe((financialDetailsResponse: HttpResponse<FinancialDetails>) => {
                        const financialDetails: FinancialDetails = financialDetailsResponse.body;
                        this.ngbModalRef = this.financialDetailsModalRef(component, financialDetails);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.financialDetailsModalRef(component, new FinancialDetails());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    financialDetailsModalRef(component: Component, financialDetails: FinancialDetails): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.financialDetails = financialDetails;
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
