import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PatokasSharedModule } from '../../shared';
import {
    DocumentTemplatesService,
    DocumentTemplatesPopupService,
    DocumentTemplatesComponent,
    DocumentTemplatesDetailComponent,
    DocumentTemplatesDialogComponent,
    DocumentTemplatesPopupComponent,
    DocumentTemplatesDeletePopupComponent,
    DocumentTemplatesDeleteDialogComponent,
    documentTemplatesRoute,
    documentTemplatesPopupRoute,
    DocumentTemplatesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...documentTemplatesRoute,
    ...documentTemplatesPopupRoute,
];

@NgModule({
    imports: [
        PatokasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DocumentTemplatesComponent,
        DocumentTemplatesDetailComponent,
        DocumentTemplatesDialogComponent,
        DocumentTemplatesDeleteDialogComponent,
        DocumentTemplatesPopupComponent,
        DocumentTemplatesDeletePopupComponent,
    ],
    entryComponents: [
        DocumentTemplatesComponent,
        DocumentTemplatesDialogComponent,
        DocumentTemplatesPopupComponent,
        DocumentTemplatesDeleteDialogComponent,
        DocumentTemplatesDeletePopupComponent,
    ],
    providers: [
        DocumentTemplatesService,
        DocumentTemplatesPopupService,
        DocumentTemplatesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PatokasDocumentTemplatesModule {}
