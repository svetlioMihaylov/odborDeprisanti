import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { EmergancyContact } from './emergancy-contact.model';
import { EmergancyContactService } from './emergancy-contact.service';

@Injectable()
export class EmergancyContactPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private emergancyContactService: EmergancyContactService

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
                this.emergancyContactService.find(id)
                    .subscribe((emergancyContactResponse: HttpResponse<EmergancyContact>) => {
                        const emergancyContact: EmergancyContact = emergancyContactResponse.body;
                        this.ngbModalRef = this.emergancyContactModalRef(component, emergancyContact);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.emergancyContactModalRef(component, new EmergancyContact());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    emergancyContactModalRef(component: Component, emergancyContact: EmergancyContact): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.emergancyContact = emergancyContact;
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
