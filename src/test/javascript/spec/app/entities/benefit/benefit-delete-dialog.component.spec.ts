/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PatokasTestModule } from '../../../test.module';
import { BenefitDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/benefit/benefit-delete-dialog.component';
import { BenefitService } from '../../../../../../main/webapp/app/entities/benefit/benefit.service';

describe('Component Tests', () => {

    describe('Benefit Management Delete Component', () => {
        let comp: BenefitDeleteDialogComponent;
        let fixture: ComponentFixture<BenefitDeleteDialogComponent>;
        let service: BenefitService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [BenefitDeleteDialogComponent],
                providers: [
                    BenefitService
                ]
            })
            .overrideTemplate(BenefitDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BenefitDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BenefitService);
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
