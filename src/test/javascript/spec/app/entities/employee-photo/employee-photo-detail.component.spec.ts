/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { PatokasTestModule } from '../../../test.module';
import { EmployeePhotoDetailComponent } from '../../../../../../main/webapp/app/entities/employee-photo/employee-photo-detail.component';
import { EmployeePhotoService } from '../../../../../../main/webapp/app/entities/employee-photo/employee-photo.service';
import { EmployeePhoto } from '../../../../../../main/webapp/app/entities/employee-photo/employee-photo.model';

describe('Component Tests', () => {

    describe('EmployeePhoto Management Detail Component', () => {
        let comp: EmployeePhotoDetailComponent;
        let fixture: ComponentFixture<EmployeePhotoDetailComponent>;
        let service: EmployeePhotoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [EmployeePhotoDetailComponent],
                providers: [
                    EmployeePhotoService
                ]
            })
            .overrideTemplate(EmployeePhotoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeePhotoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeePhotoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EmployeePhoto(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.employeePhoto).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
