import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Document } from './document.model';
import { DocumentService } from './document.service';

@Injectable()
export class DocumentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private documentService: DocumentService

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
                this.documentService.find(id)
                    .subscribe((documentResponse: HttpResponse<Document>) => {
                        const document: Document = documentResponse.body;
                        if (document.createdOn) {
                            document.createdOn = {
                                year: document.createdOn.getFullYear(),
                                month: document.createdOn.getMonth() + 1,
                                day: document.createdOn.getDate()
                            };
                        }
                        this.ngbModalRef = this.documentModalRef(component, document);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.documentModalRef(component, new Document());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    documentModalRef(component: Component, document: Document): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.document = document;
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
