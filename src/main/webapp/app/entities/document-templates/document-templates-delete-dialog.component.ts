import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DocumentTemplates } from './document-templates.model';
import { DocumentTemplatesPopupService } from './document-templates-popup.service';
import { DocumentTemplatesService } from './document-templates.service';

@Component({
    selector: 'jhi-document-templates-delete-dialog',
    templateUrl: './document-templates-delete-dialog.component.html'
})
export class DocumentTemplatesDeleteDialogComponent {

    documentTemplates: DocumentTemplates;

    constructor(
        private documentTemplatesService: DocumentTemplatesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.documentTemplatesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'documentTemplatesListModification',
                content: 'Deleted an documentTemplates'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-document-templates-delete-popup',
    template: ''
})
export class DocumentTemplatesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private documentTemplatesPopupService: DocumentTemplatesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.documentTemplatesPopupService
                .open(DocumentTemplatesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
