import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PatokasSharedModule } from '../../shared';
import {
    ContactInformationService,
    ContactInformationPopupService,
    ContactInformationComponent,
    ContactInformationDetailComponent,
    ContactInformationDialogComponent,
    ContactInformationPopupComponent,
    ContactInformationDeletePopupComponent,
    ContactInformationDeleteDialogComponent,
    contactInformationRoute,
    contactInformationPopupRoute,
    ContactInformationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...contactInformationRoute,
    ...contactInformationPopupRoute,
];

@NgModule({
    imports: [
        PatokasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ContactInformationComponent,
        ContactInformationDetailComponent,
        ContactInformationDialogComponent,
        ContactInformationDeleteDialogComponent,
        ContactInformationPopupComponent,
        ContactInformationDeletePopupComponent,
    ],
    entryComponents: [
        ContactInformationComponent,
        ContactInformationDialogComponent,
        ContactInformationPopupComponent,
        ContactInformationDeleteDialogComponent,
        ContactInformationDeletePopupComponent,
    ],
    providers: [
        ContactInformationService,
        ContactInformationPopupService,
        ContactInformationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PatokasContactInformationModule {}
