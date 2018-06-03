import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ExternalPersonComponent } from './external-person.component';
import { ExternalPersonDetailComponent } from './external-person-detail.component';
import { ExternalPersonPopupComponent } from './external-person-dialog.component';
import { ExternalPersonDeletePopupComponent } from './external-person-delete-dialog.component';

@Injectable()
export class ExternalPersonResolvePagingParams implements Resolve<any> {

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

export const externalPersonRoute: Routes = [
    {
        path: 'external-person',
        component: ExternalPersonComponent,
        resolve: {
            'pagingParams': ExternalPersonResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.externalPerson.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'external-person/:id',
        component: ExternalPersonDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.externalPerson.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const externalPersonPopupRoute: Routes = [
    {
        path: 'external-person-new',
        component: ExternalPersonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.externalPerson.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'external-person/:id/edit',
        component: ExternalPersonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.externalPerson.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'external-person/:id/delete',
        component: ExternalPersonDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.externalPerson.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
