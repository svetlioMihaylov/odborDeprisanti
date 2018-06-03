/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PatokasTestModule } from '../../../test.module';
import { ContactInformationComponent } from '../../../../../../main/webapp/app/entities/contact-information/contact-information.component';
import { ContactInformationService } from '../../../../../../main/webapp/app/entities/contact-information/contact-information.service';
import { ContactInformation } from '../../../../../../main/webapp/app/entities/contact-information/contact-information.model';

describe('Component Tests', () => {

    describe('ContactInformation Management Component', () => {
        let comp: ContactInformationComponent;
        let fixture: ComponentFixture<ContactInformationComponent>;
        let service: ContactInformationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [ContactInformationComponent],
                providers: [
                    ContactInformationService
                ]
            })
            .overrideTemplate(ContactInformationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContactInformationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactInformationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ContactInformation(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.contactInformations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
