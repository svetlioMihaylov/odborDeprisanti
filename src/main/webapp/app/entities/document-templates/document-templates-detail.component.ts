import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { DocumentTemplates } from './document-templates.model';
import { DocumentTemplatesService } from './document-templates.service';

@Component({
    selector: 'jhi-document-templates-detail',
    templateUrl: './document-templates-detail.component.html'
})
export class DocumentTemplatesDetailComponent implements OnInit, OnDestroy {

    documentTemplates: DocumentTemplates;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private documentTemplatesService: DocumentTemplatesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDocumentTemplates();
    }

    load(id) {
        this.documentTemplatesService.find(id)
            .subscribe((documentTemplatesResponse: HttpResponse<DocumentTemplates>) => {
                this.documentTemplates = documentTemplatesResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDocumentTemplates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'documentTemplatesListModification',
            (response) => this.load(this.documentTemplates.id)
        );
    }
}
