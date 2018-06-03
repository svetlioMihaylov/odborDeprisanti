/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { PatokasTestModule } from '../../../test.module';
import { VacationRequestsDetailComponent } from '../../../../../../main/webapp/app/entities/vacation-requests/vacation-requests-detail.component';
import { VacationRequestsService } from '../../../../../../main/webapp/app/entities/vacation-requests/vacation-requests.service';
import { VacationRequests } from '../../../../../../main/webapp/app/entities/vacation-requests/vacation-requests.model';

describe('Component Tests', () => {

    describe('VacationRequests Management Detail Component', () => {
        let comp: VacationRequestsDetailComponent;
        let fixture: ComponentFixture<VacationRequestsDetailComponent>;
        let service: VacationRequestsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [VacationRequestsDetailComponent],
                providers: [
                    VacationRequestsService
                ]
            })
            .overrideTemplate(VacationRequestsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VacationRequestsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VacationRequestsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new VacationRequests(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.vacationRequests).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
