/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { PatokasTestModule } from '../../../test.module';
import { EmployeePossitionDetailComponent } from '../../../../../../main/webapp/app/entities/employee-possition/employee-possition-detail.component';
import { EmployeePossitionService } from '../../../../../../main/webapp/app/entities/employee-possition/employee-possition.service';
import { EmployeePossition } from '../../../../../../main/webapp/app/entities/employee-possition/employee-possition.model';

describe('Component Tests', () => {

    describe('EmployeePossition Management Detail Component', () => {
        let comp: EmployeePossitionDetailComponent;
        let fixture: ComponentFixture<EmployeePossitionDetailComponent>;
        let service: EmployeePossitionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [EmployeePossitionDetailComponent],
                providers: [
                    EmployeePossitionService
                ]
            })
            .overrideTemplate(EmployeePossitionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeePossitionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeePossitionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EmployeePossition(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.employeePossition).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
