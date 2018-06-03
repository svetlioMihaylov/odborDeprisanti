/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PatokasTestModule } from '../../../test.module';
import { BenefitDialogComponent } from '../../../../../../main/webapp/app/entities/benefit/benefit-dialog.component';
import { BenefitService } from '../../../../../../main/webapp/app/entities/benefit/benefit.service';
import { Benefit } from '../../../../../../main/webapp/app/entities/benefit/benefit.model';
import { EmployeeService } from '../../../../../../main/webapp/app/entities/employee';
import { ExternalPersonService } from '../../../../../../main/webapp/app/entities/external-person';

describe('Component Tests', () => {

    describe('Benefit Management Dialog Component', () => {
        let comp: BenefitDialogComponent;
        let fixture: ComponentFixture<BenefitDialogComponent>;
        let service: BenefitService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PatokasTestModule],
                declarations: [BenefitDialogComponent],
                providers: [
                    EmployeeService,
                    ExternalPersonService,
                    BenefitService
                ]
            })
            .overrideTemplate(BenefitDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BenefitDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BenefitService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Benefit(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.benefit = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'benefitListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Benefit();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.benefit = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'benefitListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
