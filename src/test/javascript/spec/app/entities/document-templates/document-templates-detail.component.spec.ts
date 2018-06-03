/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { PatokasTestModule } from '../../../test.module';
import { DocumentTemplatesDetailComponent } from '../../../../../../main/webapp/app/entities/document-templates/document-templates-detail.component';
import { DocumentTemplatesService } from '../../../../../../main/webapp/app/entities/document-templates/document-templates.service';
import { DocumentTemplates } from '../../../../../../main/webapp/app/entities/document-templates/document-templates.model';

describe('Component Tests', () => {

    describe('DocumentTemplates Management Detail Component', () => {
        let comp: DocumentTemplatesDetailComponent;
        let fixture: ComponentFixture<DocumentTemplatesDetailComponent>;
        let service: DocumentTemplatesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [DocumentTemplatesDetailComponent],
                providers: [
                    DocumentTemplatesService
                ]
            })
            .overrideTemplate(DocumentTemplatesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DocumentTemplatesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentTemplatesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DocumentTemplates(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.documentTemplates).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
