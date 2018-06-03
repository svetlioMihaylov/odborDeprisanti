import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Benefit } from './benefit.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Benefit>;

@Injectable()
export class BenefitService {

    private resourceUrl =  SERVER_API_URL + 'api/benefits';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(benefit: Benefit): Observable<EntityResponseType> {
        const copy = this.convert(benefit);
        return this.http.post<Benefit>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(benefit: Benefit): Observable<EntityResponseType> {
        const copy = this.convert(benefit);
        return this.http.put<Benefit>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Benefit>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Benefit[]>> {
        const options = createRequestOption(req);
        return this.http.get<Benefit[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Benefit[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Benefit = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Benefit[]>): HttpResponse<Benefit[]> {
        const jsonResponse: Benefit[] = res.body;
        const body: Benefit[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Benefit.
     */
    private convertItemFromServer(benefit: Benefit): Benefit {
        const copy: Benefit = Object.assign({}, benefit);
        copy.startDate = this.dateUtils
            .convertLocalDateFromServer(benefit.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateFromServer(benefit.endDate);
        return copy;
    }

    /**
     * Convert a Benefit to a JSON which can be sent to the server.
     */
    private convert(benefit: Benefit): Benefit {
        const copy: Benefit = Object.assign({}, benefit);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(benefit.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(benefit.endDate);
        return copy;
    }
}
