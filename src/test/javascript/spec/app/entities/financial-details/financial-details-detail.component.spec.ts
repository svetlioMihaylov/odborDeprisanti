/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { PatokasTestModule } from '../../../test.module';
import { FinancialDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/financial-details/financial-details-detail.component';
import { FinancialDetailsService } from '../../../../../../main/webapp/app/entities/financial-details/financial-details.service';
import { FinancialDetails } from '../../../../../../main/webapp/app/entities/financial-details/financial-details.model';

describe('Component Tests', () => {

    describe('FinancialDetails Management Detail Component', () => {
        let comp: FinancialDetailsDetailComponent;
        let fixture: ComponentFixture<FinancialDetailsDetailComponent>;
        let service: FinancialDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [FinancialDetailsDetailComponent],
                providers: [
                    FinancialDetailsService
                ]
            })
            .overrideTemplate(FinancialDetailsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FinancialDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinancialDetailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new FinancialDetails(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.financialDetails).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
