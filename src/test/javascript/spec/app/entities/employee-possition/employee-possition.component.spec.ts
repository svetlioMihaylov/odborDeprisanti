/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PatokasTestModule } from '../../../test.module';
import { EmployeePossitionComponent } from '../../../../../../main/webapp/app/entities/employee-possition/employee-possition.component';
import { EmployeePossitionService } from '../../../../../../main/webapp/app/entities/employee-possition/employee-possition.service';
import { EmployeePossition } from '../../../../../../main/webapp/app/entities/employee-possition/employee-possition.model';

describe('Component Tests', () => {

    describe('EmployeePossition Management Component', () => {
        let comp: EmployeePossitionComponent;
        let fixture: ComponentFixture<EmployeePossitionComponent>;
        let service: EmployeePossitionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [EmployeePossitionComponent],
                providers: [
                    EmployeePossitionService
                ]
            })
            .overrideTemplate(EmployeePossitionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeePossitionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeePossitionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EmployeePossition(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.employeePossitions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
