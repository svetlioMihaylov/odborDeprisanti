/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { PatokasTestModule } from '../../../test.module';
import { EmergancyContactDetailComponent } from '../../../../../../main/webapp/app/entities/emergancy-contact/emergancy-contact-detail.component';
import { EmergancyContactService } from '../../../../../../main/webapp/app/entities/emergancy-contact/emergancy-contact.service';
import { EmergancyContact } from '../../../../../../main/webapp/app/entities/emergancy-contact/emergancy-contact.model';

describe('Component Tests', () => {

    describe('EmergancyContact Management Detail Component', () => {
        let comp: EmergancyContactDetailComponent;
        let fixture: ComponentFixture<EmergancyContactDetailComponent>;
        let service: EmergancyContactService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [EmergancyContactDetailComponent],
                providers: [
                    EmergancyContactService
                ]
            })
            .overrideTemplate(EmergancyContactDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmergancyContactDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmergancyContactService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EmergancyContact(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.emergancyContact).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
