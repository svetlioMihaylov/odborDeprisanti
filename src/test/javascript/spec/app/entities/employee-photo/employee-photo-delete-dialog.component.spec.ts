/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PatokasTestModule } from '../../../test.module';
import { EmployeePhotoDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/employee-photo/employee-photo-delete-dialog.component';
import { EmployeePhotoService } from '../../../../../../main/webapp/app/entities/employee-photo/employee-photo.service';

describe('Component Tests', () => {

    describe('EmployeePhoto Management Delete Component', () => {
        let comp: EmployeePhotoDeleteDialogComponent;
        let fixture: ComponentFixture<EmployeePhotoDeleteDialogComponent>;
        let service: EmployeePhotoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [EmployeePhotoDeleteDialogComponent],
                providers: [
                    EmployeePhotoService
                ]
            })
            .overrideTemplate(EmployeePhotoDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeePhotoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeePhotoService);
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
