/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PatokasTestModule } from '../../../test.module';
import { EmployeePossitionDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/employee-possition/employee-possition-delete-dialog.component';
import { EmployeePossitionService } from '../../../../../../main/webapp/app/entities/employee-possition/employee-possition.service';

describe('Component Tests', () => {

    describe('EmployeePossition Management Delete Component', () => {
        let comp: EmployeePossitionDeleteDialogComponent;
        let fixture: ComponentFixture<EmployeePossitionDeleteDialogComponent>;
        let service: EmployeePossitionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [EmployeePossitionDeleteDialogComponent],
                providers: [
                    EmployeePossitionService
                ]
            })
            .overrideTemplate(EmployeePossitionDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeePossitionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeePossitionService);
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
