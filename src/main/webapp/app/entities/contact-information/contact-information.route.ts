import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ContactInformationComponent } from './contact-information.component';
import { ContactInformationDetailComponent } from './contact-information-detail.component';
import { ContactInformationPopupComponent } from './contact-information-dialog.component';
import { ContactInformationDeletePopupComponent } from './contact-information-delete-dialog.component';

@Injectable()
export class ContactInformationResolvePagingParams implements Resolve<any> {

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

export const contactInformationRoute: Routes = [
    {
        path: 'contact-information',
        component: ContactInformationComponent,
        resolve: {
            'pagingParams': ContactInformationResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.contactInformation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'contact-information/:id',
        component: ContactInformationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.contactInformation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contactInformationPopupRoute: Routes = [
    {
        path: 'contact-information-new',
        component: ContactInformationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.contactInformation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contact-information/:id/edit',
        component: ContactInformationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.contactInformation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contact-information/:id/delete',
        component: ContactInformationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.contactInformation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
