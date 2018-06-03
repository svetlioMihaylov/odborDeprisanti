import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Benefit } from './benefit.model';
import { BenefitService } from './benefit.service';

@Injectable()
export class BenefitPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private benefitService: BenefitService

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
                this.benefitService.find(id)
                    .subscribe((benefitResponse: HttpResponse<Benefit>) => {
                        const benefit: Benefit = benefitResponse.body;
                        if (benefit.startDate) {
                            benefit.startDate = {
                                year: benefit.startDate.getFullYear(),
                                month: benefit.startDate.getMonth() + 1,
                                day: benefit.startDate.getDate()
                            };
                        }
                        if (benefit.endDate) {
                            benefit.endDate = {
                                year: benefit.endDate.getFullYear(),
                                month: benefit.endDate.getMonth() + 1,
                                day: benefit.endDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.benefitModalRef(component, benefit);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.benefitModalRef(component, new Benefit());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    benefitModalRef(component: Component, benefit: Benefit): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.benefit = benefit;
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
