/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PatokasTestModule } from '../../../test.module';
import { WorkDetailsComponent } from '../../../../../../main/webapp/app/entities/work-details/work-details.component';
import { WorkDetailsService } from '../../../../../../main/webapp/app/entities/work-details/work-details.service';
import { WorkDetails } from '../../../../../../main/webapp/app/entities/work-details/work-details.model';

describe('Component Tests', () => {

    describe('WorkDetails Management Component', () => {
        let comp: WorkDetailsComponent;
        let fixture: ComponentFixture<WorkDetailsComponent>;
        let service: WorkDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [WorkDetailsComponent],
                providers: [
                    WorkDetailsService
                ]
            })
            .overrideTemplate(WorkDetailsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WorkDetailsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WorkDetailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new WorkDetails(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.workDetails[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
