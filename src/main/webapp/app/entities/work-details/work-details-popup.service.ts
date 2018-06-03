import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { WorkDetails } from './work-details.model';
import { WorkDetailsService } from './work-details.service';

@Injectable()
export class WorkDetailsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private workDetailsService: WorkDetailsService

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
                this.workDetailsService.find(id)
                    .subscribe((workDetailsResponse: HttpResponse<WorkDetails>) => {
                        const workDetails: WorkDetails = workDetailsResponse.body;
                        if (workDetails.startDate) {
                            workDetails.startDate = {
                                year: workDetails.startDate.getFullYear(),
                                month: workDetails.startDate.getMonth() + 1,
                                day: workDetails.startDate.getDate()
                            };
                        }
                        if (workDetails.endDate) {
                            workDetails.endDate = {
                                year: workDetails.endDate.getFullYear(),
                                month: workDetails.endDate.getMonth() + 1,
                                day: workDetails.endDate.getDate()
                            };
                        }
                        if (workDetails.endOfProbationDate) {
                            workDetails.endOfProbationDate = {
                                year: workDetails.endOfProbationDate.getFullYear(),
                                month: workDetails.endOfProbationDate.getMonth() + 1,
                                day: workDetails.endOfProbationDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.workDetailsModalRef(component, workDetails);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.workDetailsModalRef(component, new WorkDetails());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    workDetailsModalRef(component: Component, workDetails: WorkDetails): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.workDetails = workDetails;
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
