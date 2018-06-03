/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PatokasTestModule } from '../../../test.module';
import { VacationRequestsComponent } from '../../../../../../main/webapp/app/entities/vacation-requests/vacation-requests.component';
import { VacationRequestsService } from '../../../../../../main/webapp/app/entities/vacation-requests/vacation-requests.service';
import { VacationRequests } from '../../../../../../main/webapp/app/entities/vacation-requests/vacation-requests.model';

describe('Component Tests', () => {

    describe('VacationRequests Management Component', () => {
        let comp: VacationRequestsComponent;
        let fixture: ComponentFixture<VacationRequestsComponent>;
        let service: VacationRequestsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [VacationRequestsComponent],
                providers: [
                    VacationRequestsService
                ]
            })
            .overrideTemplate(VacationRequestsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VacationRequestsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VacationRequestsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new VacationRequests(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.vacationRequests[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
