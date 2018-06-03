import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PatokasSharedModule } from '../../shared';
import {
    VacationRequestsService,
    VacationRequestsPopupService,
    VacationRequestsComponent,
    VacationRequestsDetailComponent,
    VacationRequestsDialogComponent,
    VacationRequestsPopupComponent,
    VacationRequestsDeletePopupComponent,
    VacationRequestsDeleteDialogComponent,
    vacationRequestsRoute,
    vacationRequestsPopupRoute,
    VacationRequestsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...vacationRequestsRoute,
    ...vacationRequestsPopupRoute,
];

@NgModule({
    imports: [
        PatokasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VacationRequestsComponent,
        VacationRequestsDetailComponent,
        VacationRequestsDialogComponent,
        VacationRequestsDeleteDialogComponent,
        VacationRequestsPopupComponent,
        VacationRequestsDeletePopupComponent,
    ],
    entryComponents: [
        VacationRequestsComponent,
        VacationRequestsDialogComponent,
        VacationRequestsPopupComponent,
        VacationRequestsDeleteDialogComponent,
        VacationRequestsDeletePopupComponent,
    ],
    providers: [
        VacationRequestsService,
        VacationRequestsPopupService,
        VacationRequestsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PatokasVacationRequestsModule {}
