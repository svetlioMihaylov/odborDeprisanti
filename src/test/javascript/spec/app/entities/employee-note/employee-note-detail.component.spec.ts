/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { PatokasTestModule } from '../../../test.module';
import { EmployeeNoteDetailComponent } from '../../../../../../main/webapp/app/entities/employee-note/employee-note-detail.component';
import { EmployeeNoteService } from '../../../../../../main/webapp/app/entities/employee-note/employee-note.service';
import { EmployeeNote } from '../../../../../../main/webapp/app/entities/employee-note/employee-note.model';

describe('Component Tests', () => {

    describe('EmployeeNote Management Detail Component', () => {
        let comp: EmployeeNoteDetailComponent;
        let fixture: ComponentFixture<EmployeeNoteDetailComponent>;
        let service: EmployeeNoteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [EmployeeNoteDetailComponent],
                providers: [
                    EmployeeNoteService
                ]
            })
            .overrideTemplate(EmployeeNoteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeNoteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeNoteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EmployeeNote(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.employeeNote).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
