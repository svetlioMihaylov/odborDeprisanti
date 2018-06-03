/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { PatokasTestModule } from '../../../test.module';
import { WorkDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/work-details/work-details-detail.component';
import { WorkDetailsService } from '../../../../../../main/webapp/app/entities/work-details/work-details.service';
import { WorkDetails } from '../../../../../../main/webapp/app/entities/work-details/work-details.model';

describe('Component Tests', () => {

    describe('WorkDetails Management Detail Component', () => {
        let comp: WorkDetailsDetailComponent;
        let fixture: ComponentFixture<WorkDetailsDetailComponent>;
        let service: WorkDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [WorkDetailsDetailComponent],
                providers: [
                    WorkDetailsService
                ]
            })
            .overrideTemplate(WorkDetailsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WorkDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WorkDetailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new WorkDetails(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.workDetails).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
