import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { IDCardComponent } from './id-card.component';
import { IDCardDetailComponent } from './id-card-detail.component';
import { IDCardPopupComponent } from './id-card-dialog.component';
import { IDCardDeletePopupComponent } from './id-card-delete-dialog.component';

@Injectable()
export class IDCardResolvePagingParams implements Resolve<any> {

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

export const iDCardRoute: Routes = [
    {
        path: 'id-card',
        component: IDCardComponent,
        resolve: {
            'pagingParams': IDCardResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.iDCard.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'id-card/:id',
        component: IDCardDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.iDCard.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const iDCardPopupRoute: Routes = [
    {
        path: 'id-card-new',
        component: IDCardPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.iDCard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'id-card/:id/edit',
        component: IDCardPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.iDCard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'id-card/:id/delete',
        component: IDCardDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.iDCard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
