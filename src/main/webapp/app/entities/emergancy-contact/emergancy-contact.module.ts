import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PatokasSharedModule } from '../../shared';
import {
    EmergancyContactService,
    EmergancyContactPopupService,
    EmergancyContactComponent,
    EmergancyContactDetailComponent,
    EmergancyContactDialogComponent,
    EmergancyContactPopupComponent,
    EmergancyContactDeletePopupComponent,
    EmergancyContactDeleteDialogComponent,
    emergancyContactRoute,
    emergancyContactPopupRoute,
    EmergancyContactResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...emergancyContactRoute,
    ...emergancyContactPopupRoute,
];

@NgModule({
    imports: [
        PatokasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmergancyContactComponent,
        EmergancyContactDetailComponent,
        EmergancyContactDialogComponent,
        EmergancyContactDeleteDialogComponent,
        EmergancyContactPopupComponent,
        EmergancyContactDeletePopupComponent,
    ],
    entryComponents: [
        EmergancyContactComponent,
        EmergancyContactDialogComponent,
        EmergancyContactPopupComponent,
        EmergancyContactDeleteDialogComponent,
        EmergancyContactDeletePopupComponent,
    ],
    providers: [
        EmergancyContactService,
        EmergancyContactPopupService,
        EmergancyContactResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PatokasEmergancyContactModule {}
