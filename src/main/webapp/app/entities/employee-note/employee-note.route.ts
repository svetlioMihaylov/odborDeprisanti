import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { EmployeeNoteComponent } from './employee-note.component';
import { EmployeeNoteDetailComponent } from './employee-note-detail.component';
import { EmployeeNotePopupComponent } from './employee-note-dialog.component';
import { EmployeeNoteDeletePopupComponent } from './employee-note-delete-dialog.component';

@Injectable()
export class EmployeeNoteResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const employeeNoteRoute: Routes = [
    {
        path: 'employee-note',
        component: EmployeeNoteComponent,
        resolve: {
            'pagingParams': EmployeeNoteResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.employeeNote.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'employee-note/:id',
        component: EmployeeNoteDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.employeeNote.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeeNotePopupRoute: Routes = [
    {
        path: 'employee-note-new',
        component: EmployeeNotePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.employeeNote.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-note/:id/edit',
        component: EmployeeNotePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.employeeNote.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-note/:id/delete',
        component: EmployeeNoteDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.employeeNote.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
