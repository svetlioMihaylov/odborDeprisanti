/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PatokasTestModule } from '../../../test.module';
import { DocumentTemplatesComponent } from '../../../../../../main/webapp/app/entities/document-templates/document-templates.component';
import { DocumentTemplatesService } from '../../../../../../main/webapp/app/entities/document-templates/document-templates.service';
import { DocumentTemplates } from '../../../../../../main/webapp/app/entities/document-templates/document-templates.model';

describe('Component Tests', () => {

    describe('DocumentTemplates Management Component', () => {
        let comp: DocumentTemplatesComponent;
        let fixture: ComponentFixture<DocumentTemplatesComponent>;
        let service: DocumentTemplatesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [DocumentTemplatesComponent],
                providers: [
                    DocumentTemplatesService
                ]
            })
            .overrideTemplate(DocumentTemplatesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DocumentTemplatesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentTemplatesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DocumentTemplates(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.documentTemplates[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
