/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PatokasTestModule } from '../../../test.module';
import { FinancialDetailsComponent } from '../../../../../../main/webapp/app/entities/financial-details/financial-details.component';
import { FinancialDetailsService } from '../../../../../../main/webapp/app/entities/financial-details/financial-details.service';
import { FinancialDetails } from '../../../../../../main/webapp/app/entities/financial-details/financial-details.model';

describe('Component Tests', () => {

    describe('FinancialDetails Management Component', () => {
        let comp: FinancialDetailsComponent;
        let fixture: ComponentFixture<FinancialDetailsComponent>;
        let service: FinancialDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [FinancialDetailsComponent],
                providers: [
                    FinancialDetailsService
                ]
            })
            .overrideTemplate(FinancialDetailsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FinancialDetailsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinancialDetailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new FinancialDetails(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.financialDetails[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
