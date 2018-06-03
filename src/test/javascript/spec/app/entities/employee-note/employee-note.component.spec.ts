/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PatokasTestModule } from '../../../test.module';
import { EmployeeNoteComponent } from '../../../../../../main/webapp/app/entities/employee-note/employee-note.component';
import { EmployeeNoteService } from '../../../../../../main/webapp/app/entities/employee-note/employee-note.service';
import { EmployeeNote } from '../../../../../../main/webapp/app/entities/employee-note/employee-note.model';

describe('Component Tests', () => {

    describe('EmployeeNote Management Component', () => {
        let comp: EmployeeNoteComponent;
        let fixture: ComponentFixture<EmployeeNoteComponent>;
        let service: EmployeeNoteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [EmployeeNoteComponent],
                providers: [
                    EmployeeNoteService
                ]
            })
            .overrideTemplate(EmployeeNoteComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeNoteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeNoteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EmployeeNote(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.employeeNotes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
