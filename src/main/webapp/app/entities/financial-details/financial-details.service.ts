import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { FinancialDetails } from './financial-details.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<FinancialDetails>;

@Injectable()
export class FinancialDetailsService {

    private resourceUrl =  SERVER_API_URL + 'api/financial-details';

    constructor(private http: HttpClient) { }

    create(financialDetails: FinancialDetails): Observable<EntityResponseType> {
        const copy = this.convert(financialDetails);
        return this.http.post<FinancialDetails>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(financialDetails: FinancialDetails): Observable<EntityResponseType> {
        const copy = this.convert(financialDetails);
        return this.http.put<FinancialDetails>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<FinancialDetails>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<FinancialDetails[]>> {
        const options = createRequestOption(req);
        return this.http.get<FinancialDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<FinancialDetails[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: FinancialDetails = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<FinancialDetails[]>): HttpResponse<FinancialDetails[]> {
        const jsonResponse: FinancialDetails[] = res.body;
        const body: FinancialDetails[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to FinancialDetails.
     */
    private convertItemFromServer(financialDetails: FinancialDetails): FinancialDetails {
        const copy: FinancialDetails = Object.assign({}, financialDetails);
        return copy;
    }

    /**
     * Convert a FinancialDetails to a JSON which can be sent to the server.
     */
    private convert(financialDetails: FinancialDetails): FinancialDetails {
        const copy: FinancialDetails = Object.assign({}, financialDetails);
        return copy;
    }
}
