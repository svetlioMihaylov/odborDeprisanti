import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PatokasSharedModule } from '../../shared';
import {
    EmployeePossitionService,
    EmployeePossitionPopupService,
    EmployeePossitionComponent,
    EmployeePossitionDetailComponent,
    EmployeePossitionDialogComponent,
    EmployeePossitionPopupComponent,
    EmployeePossitionDeletePopupComponent,
    EmployeePossitionDeleteDialogComponent,
    employeePossitionRoute,
    employeePossitionPopupRoute,
    EmployeePossitionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...employeePossitionRoute,
    ...employeePossitionPopupRoute,
];

@NgModule({
    imports: [
        PatokasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmployeePossitionComponent,
        EmployeePossitionDetailComponent,
        EmployeePossitionDialogComponent,
        EmployeePossitionDeleteDialogComponent,
        EmployeePossitionPopupComponent,
        EmployeePossitionDeletePopupComponent,
    ],
    entryComponents: [
        EmployeePossitionComponent,
        EmployeePossitionDialogComponent,
        EmployeePossitionPopupComponent,
        EmployeePossitionDeleteDialogComponent,
        EmployeePossitionDeletePopupComponent,
    ],
    providers: [
        EmployeePossitionService,
        EmployeePossitionPopupService,
        EmployeePossitionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PatokasEmployeePossitionModule {}
