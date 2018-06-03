import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PatokasSharedModule } from '../../shared';
import {
    IDCardService,
    IDCardPopupService,
    IDCardComponent,
    IDCardDetailComponent,
    IDCardDialogComponent,
    IDCardPopupComponent,
    IDCardDeletePopupComponent,
    IDCardDeleteDialogComponent,
    iDCardRoute,
    iDCardPopupRoute,
    IDCardResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...iDCardRoute,
    ...iDCardPopupRoute,
];

@NgModule({
    imports: [
        PatokasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IDCardComponent,
        IDCardDetailComponent,
        IDCardDialogComponent,
        IDCardDeleteDialogComponent,
        IDCardPopupComponent,
        IDCardDeletePopupComponent,
    ],
    entryComponents: [
        IDCardComponent,
        IDCardDialogComponent,
        IDCardPopupComponent,
        IDCardDeleteDialogComponent,
        IDCardDeletePopupComponent,
    ],
    providers: [
        IDCardService,
        IDCardPopupService,
        IDCardResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PatokasIDCardModule {}
