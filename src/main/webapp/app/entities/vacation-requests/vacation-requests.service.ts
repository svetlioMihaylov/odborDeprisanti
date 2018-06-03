import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { VacationRequests } from './vacation-requests.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<VacationRequests>;

@Injectable()
export class VacationRequestsService {

    private resourceUrl =  SERVER_API_URL + 'api/vacation-requests';

    constructor(private http: HttpClient) { }

    create(vacationRequests: VacationRequests): Observable<EntityResponseType> {
        const copy = this.convert(vacationRequests);
        return this.http.post<VacationRequests>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(vacationRequests: VacationRequests): Observable<EntityResponseType> {
        const copy = this.convert(vacationRequests);
        return this.http.put<VacationRequests>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<VacationRequests>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<VacationRequests[]>> {
        const options = createRequestOption(req);
        return this.http.get<VacationRequests[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<VacationRequests[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: VacationRequests = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<VacationRequests[]>): HttpResponse<VacationRequests[]> {
        const jsonResponse: VacationRequests[] = res.body;
        const body: VacationRequests[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to VacationRequests.
     */
    private convertItemFromServer(vacationRequests: VacationRequests): VacationRequests {
        const copy: VacationRequests = Object.assign({}, vacationRequests);
        return copy;
    }

    /**
     * Convert a VacationRequests to a JSON which can be sent to the server.
     */
    private convert(vacationRequests: VacationRequests): VacationRequests {
        const copy: VacationRequests = Object.assign({}, vacationRequests);
        return copy;
    }
}
