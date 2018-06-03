import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { VacationRequestsComponent } from './vacation-requests.component';
import { VacationRequestsDetailComponent } from './vacation-requests-detail.component';
import { VacationRequestsPopupComponent } from './vacation-requests-dialog.component';
import { VacationRequestsDeletePopupComponent } from './vacation-requests-delete-dialog.component';

@Injectable()
export class VacationRequestsResolvePagingParams implements Resolve<any> {

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

export const vacationRequestsRoute: Routes = [
    {
        path: 'vacation-requests',
        component: VacationRequestsComponent,
        resolve: {
            'pagingParams': VacationRequestsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.vacationRequests.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'vacation-requests/:id',
        component: VacationRequestsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.vacationRequests.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vacationRequestsPopupRoute: Routes = [
    {
        path: 'vacation-requests-new',
        component: VacationRequestsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.vacationRequests.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vacation-requests/:id/edit',
        component: VacationRequestsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.vacationRequests.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vacation-requests/:id/delete',
        component: VacationRequestsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.vacationRequests.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
