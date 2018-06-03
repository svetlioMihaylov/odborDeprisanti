/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PatokasTestModule } from '../../../test.module';
import { EmployeePhotoComponent } from '../../../../../../main/webapp/app/entities/employee-photo/employee-photo.component';
import { EmployeePhotoService } from '../../../../../../main/webapp/app/entities/employee-photo/employee-photo.service';
import { EmployeePhoto } from '../../../../../../main/webapp/app/entities/employee-photo/employee-photo.model';

describe('Component Tests', () => {

    describe('EmployeePhoto Management Component', () => {
        let comp: EmployeePhotoComponent;
        let fixture: ComponentFixture<EmployeePhotoComponent>;
        let service: EmployeePhotoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [EmployeePhotoComponent],
                providers: [
                    EmployeePhotoService
                ]
            })
            .overrideTemplate(EmployeePhotoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeePhotoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeePhotoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EmployeePhoto(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.employeePhotos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
