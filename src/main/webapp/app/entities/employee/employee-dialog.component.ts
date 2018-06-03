import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Employee } from './employee.model';
import { EmployeePopupService } from './employee-popup.service';
import { EmployeeService } from './employee.service';
import { ContactInformation, ContactInformationService } from '../contact-information';
import { EmployeePhoto, EmployeePhotoService } from '../employee-photo';
import { WorkDetails, WorkDetailsService } from '../work-details';
import { EmergancyContact, EmergancyContactService } from '../emergancy-contact';
import { IDCard, IDCardService } from '../id-card';
import { FinancialDetails, FinancialDetailsService } from '../financial-details';

@Component({
    selector: 'jhi-employee-dialog',
    templateUrl: './employee-dialog.component.html'
})
export class EmployeeDialogComponent implements OnInit {

    employee: Employee;
    isSaving: boolean;

    contactinformations: ContactInformation[];

    employeephotos: EmployeePhoto[];

    workdetails: WorkDetails[];

    emargencycontacts: EmergancyContact[];

    idcards: IDCard[];

    financialdetails: FinancialDetails[];
    dateOfBirthDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private employeeService: EmployeeService,
        private contactInformationService: ContactInformationService,
        private employeePhotoService: EmployeePhotoService,
        private workDetailsService: WorkDetailsService,
        private emergancyContactService: EmergancyContactService,
        private iDCardService: IDCardService,
        private financialDetailsService: FinancialDetailsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.contactInformationService
            .query({filter: 'employee-is-null'})
            .subscribe((res: HttpResponse<ContactInformation[]>) => {
                if (!this.employee.contactInformation || !this.employee.contactInformation.id) {
                    this.contactinformations = res.body;
                } else {
                    this.contactInformationService
                        .find(this.employee.contactInformation.id)
                        .subscribe((subRes: HttpResponse<ContactInformation>) => {
                            this.contactinformations = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.employeePhotoService
            .query({filter: 'employee-is-null'})
            .subscribe((res: HttpResponse<EmployeePhoto[]>) => {
                if (!this.employee.employeePhoto || !this.employee.employeePhoto.id) {
                    this.employeephotos = res.body;
                } else {
                    this.employeePhotoService
                        .find(this.employee.employeePhoto.id)
                        .subscribe((subRes: HttpResponse<EmployeePhoto>) => {
                            this.employeephotos = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.workDetailsService
            .query({filter: 'employee-is-null'})
            .subscribe((res: HttpResponse<WorkDetails[]>) => {
                if (!this.employee.workDetails || !this.employee.workDetails.id) {
                    this.workdetails = res.body;
                } else {
                    this.workDetailsService
                        .find(this.employee.workDetails.id)
                        .subscribe((subRes: HttpResponse<WorkDetails>) => {
                            this.workdetails = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.emergancyContactService
            .query({filter: 'employee-is-null'})
            .subscribe((res: HttpResponse<EmergancyContact[]>) => {
                if (!this.employee.emargencyContact || !this.employee.emargencyContact.id) {
                    this.emargencycontacts = res.body;
                } else {
                    this.emergancyContactService
                        .find(this.employee.emargencyContact.id)
                        .subscribe((subRes: HttpResponse<EmergancyContact>) => {
                            this.emargencycontacts = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.iDCardService
            .query({filter: 'employee-is-null'})
            .subscribe((res: HttpResponse<IDCard[]>) => {
                if (!this.employee.idcard || !this.employee.idcard.id) {
                    this.idcards = res.body;
                } else {
                    this.iDCardService
                        .find(this.employee.idcard.id)
                        .subscribe((subRes: HttpResponse<IDCard>) => {
                            this.idcards = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.financialDetailsService
            .query({filter: 'employee-is-null'})
            .subscribe((res: HttpResponse<FinancialDetails[]>) => {
                if (!this.employee.financialDetails || !this.employee.financialDetails.id) {
                    this.financialdetails = res.body;
                } else {
                    this.financialDetailsService
                        .find(this.employee.financialDetails.id)
                        .subscribe((subRes: HttpResponse<FinancialDetails>) => {
                            this.financialdetails = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.employee.id !== undefined) {
            this.subscribeToSaveResponse(
                this.employeeService.update(this.employee));
        } else {
            this.subscribeToSaveResponse(
                this.employeeService.create(this.employee));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Employee>>) {
        result.subscribe((res: HttpResponse<Employee>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Employee) {
        this.eventManager.broadcast({ name: 'employeeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackContactInformationById(index: number, item: ContactInformation) {
        return item.id;
    }

    trackEmployeePhotoById(index: number, item: EmployeePhoto) {
        return item.id;
    }

    trackWorkDetailsById(index: number, item: WorkDetails) {
        return item.id;
    }

    trackEmergancyContactById(index: number, item: EmergancyContact) {
        return item.id;
    }

    trackIDCardById(index: number, item: IDCard) {
        return item.id;
    }

    trackFinancialDetailsById(index: number, item: FinancialDetails) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-employee-popup',
    template: ''
})
export class EmployeePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeePopupService: EmployeePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.employeePopupService
                    .open(EmployeeDialogComponent as Component, params['id']);
            } else {
                this.employeePopupService
                    .open(EmployeeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
