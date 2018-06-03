import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PatokasSharedModule } from '../../shared';
import {
    ExternalPersonService,
    ExternalPersonPopupService,
    ExternalPersonComponent,
    ExternalPersonDetailComponent,
    ExternalPersonDialogComponent,
    ExternalPersonPopupComponent,
    ExternalPersonDeletePopupComponent,
    ExternalPersonDeleteDialogComponent,
    externalPersonRoute,
    externalPersonPopupRoute,
    ExternalPersonResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...externalPersonRoute,
    ...externalPersonPopupRoute,
];

@NgModule({
    imports: [
        PatokasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ExternalPersonComponent,
        ExternalPersonDetailComponent,
        ExternalPersonDialogComponent,
        ExternalPersonDeleteDialogComponent,
        ExternalPersonPopupComponent,
        ExternalPersonDeletePopupComponent,
    ],
    entryComponents: [
        ExternalPersonComponent,
        ExternalPersonDialogComponent,
        ExternalPersonPopupComponent,
        ExternalPersonDeleteDialogComponent,
        ExternalPersonDeletePopupComponent,
    ],
    providers: [
        ExternalPersonService,
        ExternalPersonPopupService,
        ExternalPersonResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PatokasExternalPersonModule {}
