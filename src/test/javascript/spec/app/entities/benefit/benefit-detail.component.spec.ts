/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { PatokasTestModule } from '../../../test.module';
import { BenefitDetailComponent } from '../../../../../../main/webapp/app/entities/benefit/benefit-detail.component';
import { BenefitService } from '../../../../../../main/webapp/app/entities/benefit/benefit.service';
import { Benefit } from '../../../../../../main/webapp/app/entities/benefit/benefit.model';

describe('Component Tests', () => {

    describe('Benefit Management Detail Component', () => {
        let comp: BenefitDetailComponent;
        let fixture: ComponentFixture<BenefitDetailComponent>;
        let service: BenefitService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [BenefitDetailComponent],
                providers: [
                    BenefitService
                ]
            })
            .overrideTemplate(BenefitDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BenefitDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BenefitService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Benefit(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.benefit).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
