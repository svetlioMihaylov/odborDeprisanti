import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PatokasSharedModule } from '../../shared';
import {
    EmployeePhotoService,
    EmployeePhotoPopupService,
    EmployeePhotoComponent,
    EmployeePhotoDetailComponent,
    EmployeePhotoDialogComponent,
    EmployeePhotoPopupComponent,
    EmployeePhotoDeletePopupComponent,
    EmployeePhotoDeleteDialogComponent,
    employeePhotoRoute,
    employeePhotoPopupRoute,
    EmployeePhotoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...employeePhotoRoute,
    ...employeePhotoPopupRoute,
];

@NgModule({
    imports: [
        PatokasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmployeePhotoComponent,
        EmployeePhotoDetailComponent,
        EmployeePhotoDialogComponent,
        EmployeePhotoDeleteDialogComponent,
        EmployeePhotoPopupComponent,
        EmployeePhotoDeletePopupComponent,
    ],
    entryComponents: [
        EmployeePhotoComponent,
        EmployeePhotoDialogComponent,
        EmployeePhotoPopupComponent,
        EmployeePhotoDeleteDialogComponent,
        EmployeePhotoDeletePopupComponent,
    ],
    providers: [
        EmployeePhotoService,
        EmployeePhotoPopupService,
        EmployeePhotoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PatokasEmployeePhotoModule {}
