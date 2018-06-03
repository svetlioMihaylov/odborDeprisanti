import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PatokasSharedModule } from '../../shared';
import {
    BenefitService,
    BenefitPopupService,
    BenefitComponent,
    BenefitDetailComponent,
    BenefitDialogComponent,
    BenefitPopupComponent,
    BenefitDeletePopupComponent,
    BenefitDeleteDialogComponent,
    benefitRoute,
    benefitPopupRoute,
    BenefitResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...benefitRoute,
    ...benefitPopupRoute,
];

@NgModule({
    imports: [
        PatokasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BenefitComponent,
        BenefitDetailComponent,
        BenefitDialogComponent,
        BenefitDeleteDialogComponent,
        BenefitPopupComponent,
        BenefitDeletePopupComponent,
    ],
    entryComponents: [
        BenefitComponent,
        BenefitDialogComponent,
        BenefitPopupComponent,
        BenefitDeleteDialogComponent,
        BenefitDeletePopupComponent,
    ],
    providers: [
        BenefitService,
        BenefitPopupService,
        BenefitResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PatokasBenefitModule {}
