import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PatokasSharedModule } from '../../shared';
import {
    EmployeeNoteService,
    EmployeeNotePopupService,
    EmployeeNoteComponent,
    EmployeeNoteDetailComponent,
    EmployeeNoteDialogComponent,
    EmployeeNotePopupComponent,
    EmployeeNoteDeletePopupComponent,
    EmployeeNoteDeleteDialogComponent,
    employeeNoteRoute,
    employeeNotePopupRoute,
    EmployeeNoteResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...employeeNoteRoute,
    ...employeeNotePopupRoute,
];

@NgModule({
    imports: [
        PatokasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmployeeNoteComponent,
        EmployeeNoteDetailComponent,
        EmployeeNoteDialogComponent,
        EmployeeNoteDeleteDialogComponent,
        EmployeeNotePopupComponent,
        EmployeeNoteDeletePopupComponent,
    ],
    entryComponents: [
        EmployeeNoteComponent,
        EmployeeNoteDialogComponent,
        EmployeeNotePopupComponent,
        EmployeeNoteDeleteDialogComponent,
        EmployeeNoteDeletePopupComponent,
    ],
    providers: [
        EmployeeNoteService,
        EmployeeNotePopupService,
        EmployeeNoteResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PatokasEmployeeNoteModule {}
