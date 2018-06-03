/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PatokasTestModule } from '../../../test.module';
import { EmergancyContactComponent } from '../../../../../../main/webapp/app/entities/emergancy-contact/emergancy-contact.component';
import { EmergancyContactService } from '../../../../../../main/webapp/app/entities/emergancy-contact/emergancy-contact.service';
import { EmergancyContact } from '../../../../../../main/webapp/app/entities/emergancy-contact/emergancy-contact.model';

describe('Component Tests', () => {

    describe('EmergancyContact Management Component', () => {
        let comp: EmergancyContactComponent;
        let fixture: ComponentFixture<EmergancyContactComponent>;
        let service: EmergancyContactService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [EmergancyContactComponent],
                providers: [
                    EmergancyContactService
                ]
            })
            .overrideTemplate(EmergancyContactComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmergancyContactComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmergancyContactService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EmergancyContact(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.emergancyContacts[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
