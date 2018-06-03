import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { EmployeePhotoComponent } from './employee-photo.component';
import { EmployeePhotoDetailComponent } from './employee-photo-detail.component';
import { EmployeePhotoPopupComponent } from './employee-photo-dialog.component';
import { EmployeePhotoDeletePopupComponent } from './employee-photo-delete-dialog.component';

@Injectable()
export class EmployeePhotoResolvePagingParams implements Resolve<any> {

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

export const employeePhotoRoute: Routes = [
    {
        path: 'employee-photo',
        component: EmployeePhotoComponent,
        resolve: {
            'pagingParams': EmployeePhotoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.employeePhoto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'employee-photo/:id',
        component: EmployeePhotoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.employeePhoto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeePhotoPopupRoute: Routes = [
    {
        path: 'employee-photo-new',
        component: EmployeePhotoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.employeePhoto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-photo/:id/edit',
        component: EmployeePhotoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.employeePhoto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-photo/:id/delete',
        component: EmployeePhotoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.employeePhoto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
