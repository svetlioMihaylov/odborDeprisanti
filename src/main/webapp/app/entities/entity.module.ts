import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PatokasEmployeeModule } from './employee/employee.module';
import { PatokasContactInformationModule } from './contact-information/contact-information.module';
import { PatokasEmployeePhotoModule } from './employee-photo/employee-photo.module';
import { PatokasEmployeePossitionModule } from './employee-possition/employee-possition.module';
import { PatokasEmergancyContactModule } from './emergancy-contact/emergancy-contact.module';
import { PatokasFinancialDetailsModule } from './financial-details/financial-details.module';
import { PatokasWorkDetailsModule } from './work-details/work-details.module';
import { PatokasVacationRequestsModule } from './vacation-requests/vacation-requests.module';
import { PatokasIDCardModule } from './id-card/id-card.module';
import { PatokasExternalPersonModule } from './external-person/external-person.module';
import { PatokasBenefitModule } from './benefit/benefit.module';
import { PatokasDocumentModule } from './document/document.module';
import { PatokasEmployeeNoteModule } from './employee-note/employee-note.module';
import { PatokasDocumentTemplatesModule } from './document-templates/document-templates.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        PatokasEmployeeModule,
        PatokasContactInformationModule,
        PatokasEmployeePhotoModule,
        PatokasEmployeePossitionModule,
        PatokasEmergancyContactModule,
        PatokasFinancialDetailsModule,
        PatokasWorkDetailsModule,
        PatokasVacationRequestsModule,
        PatokasIDCardModule,
        PatokasExternalPersonModule,
        PatokasBenefitModule,
        PatokasDocumentModule,
        PatokasEmployeeNoteModule,
        PatokasDocumentTemplatesModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PatokasEntityModule {}
