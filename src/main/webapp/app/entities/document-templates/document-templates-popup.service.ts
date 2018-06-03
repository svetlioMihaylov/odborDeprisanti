import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DocumentTemplates } from './document-templates.model';
import { DocumentTemplatesService } from './document-templates.service';

@Injectable()
export class DocumentTemplatesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private documentTemplatesService: DocumentTemplatesService

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
                this.documentTemplatesService.find(id)
                    .subscribe((documentTemplatesResponse: HttpResponse<DocumentTemplates>) => {
                        const documentTemplates: DocumentTemplates = documentTemplatesResponse.body;
                        this.ngbModalRef = this.documentTemplatesModalRef(component, documentTemplates);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.documentTemplatesModalRef(component, new DocumentTemplates());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    documentTemplatesModalRef(component: Component, documentTemplates: DocumentTemplates): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.documentTemplates = documentTemplates;
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
