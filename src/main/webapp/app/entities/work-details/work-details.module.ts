import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PatokasSharedModule } from '../../shared';
import {
    WorkDetailsService,
    WorkDetailsPopupService,
    WorkDetailsComponent,
    WorkDetailsDetailComponent,
    WorkDetailsDialogComponent,
    WorkDetailsPopupComponent,
    WorkDetailsDeletePopupComponent,
    WorkDetailsDeleteDialogComponent,
    workDetailsRoute,
    workDetailsPopupRoute,
    WorkDetailsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...workDetailsRoute,
    ...workDetailsPopupRoute,
];

@NgModule({
    imports: [
        PatokasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        WorkDetailsComponent,
        WorkDetailsDetailComponent,
        WorkDetailsDialogComponent,
        WorkDetailsDeleteDialogComponent,
        WorkDetailsPopupComponent,
        WorkDetailsDeletePopupComponent,
    ],
    entryComponents: [
        WorkDetailsComponent,
        WorkDetailsDialogComponent,
        WorkDetailsPopupComponent,
        WorkDetailsDeleteDialogComponent,
        WorkDetailsDeletePopupComponent,
    ],
    providers: [
        WorkDetailsService,
        WorkDetailsPopupService,
        WorkDetailsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PatokasWorkDetailsModule {}
