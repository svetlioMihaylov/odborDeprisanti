import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { WorkDetailsComponent } from './work-details.component';
import { WorkDetailsDetailComponent } from './work-details-detail.component';
import { WorkDetailsPopupComponent } from './work-details-dialog.component';
import { WorkDetailsDeletePopupComponent } from './work-details-delete-dialog.component';

@Injectable()
export class WorkDetailsResolvePagingParams implements Resolve<any> {

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

export const workDetailsRoute: Routes = [
    {
        path: 'work-details',
        component: WorkDetailsComponent,
        resolve: {
            'pagingParams': WorkDetailsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.workDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'work-details/:id',
        component: WorkDetailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.workDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const workDetailsPopupRoute: Routes = [
    {
        path: 'work-details-new',
        component: WorkDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.workDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'work-details/:id/edit',
        component: WorkDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.workDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'work-details/:id/delete',
        component: WorkDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.workDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
