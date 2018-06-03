import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { EmergancyContactComponent } from './emergancy-contact.component';
import { EmergancyContactDetailComponent } from './emergancy-contact-detail.component';
import { EmergancyContactPopupComponent } from './emergancy-contact-dialog.component';
import { EmergancyContactDeletePopupComponent } from './emergancy-contact-delete-dialog.component';

@Injectable()
export class EmergancyContactResolvePagingParams implements Resolve<any> {

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

export const emergancyContactRoute: Routes = [
    {
        path: 'emergancy-contact',
        component: EmergancyContactComponent,
        resolve: {
            'pagingParams': EmergancyContactResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.emergancyContact.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'emergancy-contact/:id',
        component: EmergancyContactDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.emergancyContact.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const emergancyContactPopupRoute: Routes = [
    {
        path: 'emergancy-contact-new',
        component: EmergancyContactPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.emergancyContact.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'emergancy-contact/:id/edit',
        component: EmergancyContactPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.emergancyContact.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'emergancy-contact/:id/delete',
        component: EmergancyContactDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.emergancyContact.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
