/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PatokasTestModule } from '../../../test.module';
import { EmergancyContactDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/emergancy-contact/emergancy-contact-delete-dialog.component';
import { EmergancyContactService } from '../../../../../../main/webapp/app/entities/emergancy-contact/emergancy-contact.service';

describe('Component Tests', () => {

    describe('EmergancyContact Management Delete Component', () => {
        let comp: EmergancyContactDeleteDialogComponent;
        let fixture: ComponentFixture<EmergancyContactDeleteDialogComponent>;
        let service: EmergancyContactService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [EmergancyContactDeleteDialogComponent],
                providers: [
                    EmergancyContactService
                ]
            })
            .overrideTemplate(EmergancyContactDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmergancyContactDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmergancyContactService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
