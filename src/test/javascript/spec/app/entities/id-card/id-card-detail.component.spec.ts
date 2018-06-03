/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { PatokasTestModule } from '../../../test.module';
import { IDCardDetailComponent } from '../../../../../../main/webapp/app/entities/id-card/id-card-detail.component';
import { IDCardService } from '../../../../../../main/webapp/app/entities/id-card/id-card.service';
import { IDCard } from '../../../../../../main/webapp/app/entities/id-card/id-card.model';

describe('Component Tests', () => {

    describe('IDCard Management Detail Component', () => {
        let comp: IDCardDetailComponent;
        let fixture: ComponentFixture<IDCardDetailComponent>;
        let service: IDCardService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [IDCardDetailComponent],
                providers: [
                    IDCardService
                ]
            })
            .overrideTemplate(IDCardDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IDCardDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IDCardService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new IDCard(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.iDCard).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
