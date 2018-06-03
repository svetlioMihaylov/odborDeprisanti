/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { PatokasTestModule } from '../../../test.module';
import { ContactInformationDetailComponent } from '../../../../../../main/webapp/app/entities/contact-information/contact-information-detail.component';
import { ContactInformationService } from '../../../../../../main/webapp/app/entities/contact-information/contact-information.service';
import { ContactInformation } from '../../../../../../main/webapp/app/entities/contact-information/contact-information.model';

describe('Component Tests', () => {

    describe('ContactInformation Management Detail Component', () => {
        let comp: ContactInformationDetailComponent;
        let fixture: ComponentFixture<ContactInformationDetailComponent>;
        let service: ContactInformationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [ContactInformationDetailComponent],
                providers: [
                    ContactInformationService
                ]
            })
            .overrideTemplate(ContactInformationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContactInformationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactInformationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ContactInformation(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.contactInformation).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
