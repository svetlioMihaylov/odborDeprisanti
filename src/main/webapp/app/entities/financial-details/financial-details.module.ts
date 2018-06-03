import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PatokasSharedModule } from '../../shared';
import {
    FinancialDetailsService,
    FinancialDetailsPopupService,
    FinancialDetailsComponent,
    FinancialDetailsDetailComponent,
    FinancialDetailsDialogComponent,
    FinancialDetailsPopupComponent,
    FinancialDetailsDeletePopupComponent,
    FinancialDetailsDeleteDialogComponent,
    financialDetailsRoute,
    financialDetailsPopupRoute,
    FinancialDetailsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...financialDetailsRoute,
    ...financialDetailsPopupRoute,
];

@NgModule({
    imports: [
        PatokasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FinancialDetailsComponent,
        FinancialDetailsDetailComponent,
        FinancialDetailsDialogComponent,
        FinancialDetailsDeleteDialogComponent,
        FinancialDetailsPopupComponent,
        FinancialDetailsDeletePopupComponent,
    ],
    entryComponents: [
        FinancialDetailsComponent,
        FinancialDetailsDialogComponent,
        FinancialDetailsPopupComponent,
        FinancialDetailsDeleteDialogComponent,
        FinancialDetailsDeletePopupComponent,
    ],
    providers: [
        FinancialDetailsService,
        FinancialDetailsPopupService,
        FinancialDetailsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PatokasFinancialDetailsModule {}
