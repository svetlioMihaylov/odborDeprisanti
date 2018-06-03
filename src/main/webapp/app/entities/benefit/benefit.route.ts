import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BenefitComponent } from './benefit.component';
import { BenefitDetailComponent } from './benefit-detail.component';
import { BenefitPopupComponent } from './benefit-dialog.component';
import { BenefitDeletePopupComponent } from './benefit-delete-dialog.component';

@Injectable()
export class BenefitResolvePagingParams implements Resolve<any> {

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

export const benefitRoute: Routes = [
    {
        path: 'benefit',
        component: BenefitComponent,
        resolve: {
            'pagingParams': BenefitResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.benefit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'benefit/:id',
        component: BenefitDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.benefit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const benefitPopupRoute: Routes = [
    {
        path: 'benefit-new',
        component: BenefitPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.benefit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'benefit/:id/edit',
        component: BenefitPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.benefit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'benefit/:id/delete',
        component: BenefitDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.benefit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
