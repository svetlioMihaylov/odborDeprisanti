/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PatokasTestModule } from '../../../test.module';
import { VacationRequestsDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/vacation-requests/vacation-requests-delete-dialog.component';
import { VacationRequestsService } from '../../../../../../main/webapp/app/entities/vacation-requests/vacation-requests.service';

describe('Component Tests', () => {

    describe('VacationRequests Management Delete Component', () => {
        let comp: VacationRequestsDeleteDialogComponent;
        let fixture: ComponentFixture<VacationRequestsDeleteDialogComponent>;
        let service: VacationRequestsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [VacationRequestsDeleteDialogComponent],
                providers: [
                    VacationRequestsService
                ]
            })
            .overrideTemplate(VacationRequestsDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VacationRequestsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VacationRequestsService);
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
