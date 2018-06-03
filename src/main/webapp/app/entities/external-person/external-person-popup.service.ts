import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ExternalPerson } from './external-person.model';
import { ExternalPersonService } from './external-person.service';

@Injectable()
export class ExternalPersonPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private externalPersonService: ExternalPersonService

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
                this.externalPersonService.find(id)
                    .subscribe((externalPersonResponse: HttpResponse<ExternalPerson>) => {
                        const externalPerson: ExternalPerson = externalPersonResponse.body;
                        this.ngbModalRef = this.externalPersonModalRef(component, externalPerson);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.externalPersonModalRef(component, new ExternalPerson());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    externalPersonModalRef(component: Component, externalPerson: ExternalPerson): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.externalPerson = externalPerson;
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
