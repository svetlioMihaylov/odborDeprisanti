/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PatokasTestModule } from '../../../test.module';
import { BenefitComponent } from '../../../../../../main/webapp/app/entities/benefit/benefit.component';
import { BenefitService } from '../../../../../../main/webapp/app/entities/benefit/benefit.service';
import { Benefit } from '../../../../../../main/webapp/app/entities/benefit/benefit.model';

describe('Component Tests', () => {

    describe('Benefit Management Component', () => {
        let comp: BenefitComponent;
        let fixture: ComponentFixture<BenefitComponent>;
        let service: BenefitService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [BenefitComponent],
                providers: [
                    BenefitService
                ]
            })
            .overrideTemplate(BenefitComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BenefitComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BenefitService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Benefit(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.benefits[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
