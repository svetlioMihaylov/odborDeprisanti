import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { IDCard } from './id-card.model';
import { IDCardService } from './id-card.service';

@Injectable()
export class IDCardPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private iDCardService: IDCardService

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
                this.iDCardService.find(id)
                    .subscribe((iDCardResponse: HttpResponse<IDCard>) => {
                        const iDCard: IDCard = iDCardResponse.body;
                        if (iDCard.dateOfIssue) {
                            iDCard.dateOfIssue = {
                                year: iDCard.dateOfIssue.getFullYear(),
                                month: iDCard.dateOfIssue.getMonth() + 1,
                                day: iDCard.dateOfIssue.getDate()
                            };
                        }
                        if (iDCard.dateOfExpiry) {
                            iDCard.dateOfExpiry = {
                                year: iDCard.dateOfExpiry.getFullYear(),
                                month: iDCard.dateOfExpiry.getMonth() + 1,
                                day: iDCard.dateOfExpiry.getDate()
                            };
                        }
                        this.ngbModalRef = this.iDCardModalRef(component, iDCard);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.iDCardModalRef(component, new IDCard());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    iDCardModalRef(component: Component, iDCard: IDCard): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.iDCard = iDCard;
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
