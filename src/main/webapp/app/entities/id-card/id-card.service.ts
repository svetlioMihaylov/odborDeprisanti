import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { IDCard } from './id-card.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<IDCard>;

@Injectable()
export class IDCardService {

    private resourceUrl =  SERVER_API_URL + 'api/id-cards';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(iDCard: IDCard): Observable<EntityResponseType> {
        const copy = this.convert(iDCard);
        return this.http.post<IDCard>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(iDCard: IDCard): Observable<EntityResponseType> {
        const copy = this.convert(iDCard);
        return this.http.put<IDCard>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDCard>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<IDCard[]>> {
        const options = createRequestOption(req);
        return this.http.get<IDCard[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<IDCard[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: IDCard = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<IDCard[]>): HttpResponse<IDCard[]> {
        const jsonResponse: IDCard[] = res.body;
        const body: IDCard[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to IDCard.
     */
    private convertItemFromServer(iDCard: IDCard): IDCard {
        const copy: IDCard = Object.assign({}, iDCard);
        copy.dateOfIssue = this.dateUtils
            .convertLocalDateFromServer(iDCard.dateOfIssue);
        copy.dateOfExpiry = this.dateUtils
            .convertLocalDateFromServer(iDCard.dateOfExpiry);
        return copy;
    }

    /**
     * Convert a IDCard to a JSON which can be sent to the server.
     */
    private convert(iDCard: IDCard): IDCard {
        const copy: IDCard = Object.assign({}, iDCard);
        copy.dateOfIssue = this.dateUtils
            .convertLocalDateToServer(iDCard.dateOfIssue);
        copy.dateOfExpiry = this.dateUtils
            .convertLocalDateToServer(iDCard.dateOfExpiry);
        return copy;
    }
}
