/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PatokasTestModule } from '../../../test.module';
import { IDCardComponent } from '../../../../../../main/webapp/app/entities/id-card/id-card.component';
import { IDCardService } from '../../../../../../main/webapp/app/entities/id-card/id-card.service';
import { IDCard } from '../../../../../../main/webapp/app/entities/id-card/id-card.model';

describe('Component Tests', () => {

    describe('IDCard Management Component', () => {
        let comp: IDCardComponent;
        let fixture: ComponentFixture<IDCardComponent>;
        let service: IDCardService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [IDCardComponent],
                providers: [
                    IDCardService
                ]
            })
            .overrideTemplate(IDCardComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IDCardComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IDCardService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new IDCard(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.iDCards[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
