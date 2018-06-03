/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { PatokasTestModule } from '../../../test.module';
import { ExternalPersonDetailComponent } from '../../../../../../main/webapp/app/entities/external-person/external-person-detail.component';
import { ExternalPersonService } from '../../../../../../main/webapp/app/entities/external-person/external-person.service';
import { ExternalPerson } from '../../../../../../main/webapp/app/entities/external-person/external-person.model';

describe('Component Tests', () => {

    describe('ExternalPerson Management Detail Component', () => {
        let comp: ExternalPersonDetailComponent;
        let fixture: ComponentFixture<ExternalPersonDetailComponent>;
        let service: ExternalPersonService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [ExternalPersonDetailComponent],
                providers: [
                    ExternalPersonService
                ]
            })
            .overrideTemplate(ExternalPersonDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExternalPersonDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExternalPersonService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ExternalPerson(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.externalPerson).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
