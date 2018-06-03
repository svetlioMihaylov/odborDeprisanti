/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PatokasTestModule } from '../../../test.module';
import { ExternalPersonComponent } from '../../../../../../main/webapp/app/entities/external-person/external-person.component';
import { ExternalPersonService } from '../../../../../../main/webapp/app/entities/external-person/external-person.service';
import { ExternalPerson } from '../../../../../../main/webapp/app/entities/external-person/external-person.model';

describe('Component Tests', () => {

    describe('ExternalPerson Management Component', () => {
        let comp: ExternalPersonComponent;
        let fixture: ComponentFixture<ExternalPersonComponent>;
        let service: ExternalPersonService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [ExternalPersonComponent],
                providers: [
                    ExternalPersonService
                ]
            })
            .overrideTemplate(ExternalPersonComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExternalPersonComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExternalPersonService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ExternalPerson(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.externalPeople[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
