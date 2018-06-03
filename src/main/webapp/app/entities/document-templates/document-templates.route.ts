import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DocumentTemplatesComponent } from './document-templates.component';
import { DocumentTemplatesDetailComponent } from './document-templates-detail.component';
import { DocumentTemplatesPopupComponent } from './document-templates-dialog.component';
import { DocumentTemplatesDeletePopupComponent } from './document-templates-delete-dialog.component';

@Injectable()
export class DocumentTemplatesResolvePagingParams implements Resolve<any> {

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

export const documentTemplatesRoute: Routes = [
    {
        path: 'document-templates',
        component: DocumentTemplatesComponent,
        resolve: {
            'pagingParams': DocumentTemplatesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.documentTemplates.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'document-templates/:id',
        component: DocumentTemplatesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.documentTemplates.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const documentTemplatesPopupRoute: Routes = [
    {
        path: 'document-templates-new',
        component: DocumentTemplatesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.documentTemplates.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'document-templates/:id/edit',
        component: DocumentTemplatesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.documentTemplates.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'document-templates/:id/delete',
        component: DocumentTemplatesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'patokasApp.documentTemplates.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
