import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { EmployeePossitionComponent } from './employee-possition.component';
import { EmployeePossitionDetailComponent } from './employee-possition-detail.component';
import { EmployeePossitionPopupComponent } from './employee-possition-dialog.component';
import { EmployeePossitionDeletePopupComponent } from './employee-possition-delete-dialog.component';

@Injectable()
export class EmployeePossitionResolvePagingParams implements Resolve<any> {

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

export const employeePossitionRoute: Routes = [
    {
        path: 'employee-possition',
        component: EmployeePossitionComponent,
        resolve: {
            'pagingParams': EmployeePossitionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.employeePossition.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'employee-possition/:id',
        component: EmployeePossitionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.employeePossition.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeePossitionPopupRoute: Routes = [
    {
        path: 'employee-possition-new',
        component: EmployeePossitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.employeePossition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-possition/:id/edit',
        component: EmployeePossitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.employeePossition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-possition/:id/delete',
        component: EmployeePossitionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.employeePossition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
