import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { FinancialDetailsComponent } from './financial-details.component';
import { FinancialDetailsDetailComponent } from './financial-details-detail.component';
import { FinancialDetailsPopupComponent } from './financial-details-dialog.component';
import { FinancialDetailsDeletePopupComponent } from './financial-details-delete-dialog.component';

@Injectable()
export class FinancialDetailsResolvePagingParams implements Resolve<any> {

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

export const financialDetailsRoute: Routes = [
    {
        path: 'financial-details',
        component: FinancialDetailsComponent,
        resolve: {
            'pagingParams': FinancialDetailsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.financialDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'financial-details/:id',
        component: FinancialDetailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.financialDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const financialDetailsPopupRoute: Routes = [
    {
        path: 'financial-details-new',
        component: FinancialDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.financialDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'financial-details/:id/edit',
        component: FinancialDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.financialDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'financial-details/:id/delete',
        component: FinancialDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.financialDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
