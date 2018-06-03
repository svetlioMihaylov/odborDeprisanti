import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ExternalPerson } from './external-person.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ExternalPerson>;

@Injectable()
export class ExternalPersonService {

    private resourceUrl =  SERVER_API_URL + 'api/external-people';

    constructor(private http: HttpClient) { }

    create(externalPerson: ExternalPerson): Observable<EntityResponseType> {
        const copy = this.convert(externalPerson);
        return this.http.post<ExternalPerson>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(externalPerson: ExternalPerson): Observable<EntityResponseType> {
        const copy = this.convert(externalPerson);
        return this.http.put<ExternalPerson>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ExternalPerson>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ExternalPerson[]>> {
        const options = createRequestOption(req);
        return this.http.get<ExternalPerson[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ExternalPerson[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ExternalPerson = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ExternalPerson[]>): HttpResponse<ExternalPerson[]> {
        const jsonResponse: ExternalPerson[] = res.body;
        const body: ExternalPerson[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ExternalPerson.
     */
    private convertItemFromServer(externalPerson: ExternalPerson): ExternalPerson {
        const copy: ExternalPerson = Object.assign({}, externalPerson);
        return copy;
    }

    /**
     * Convert a ExternalPerson to a JSON which can be sent to the server.
     */
    private convert(externalPerson: ExternalPerson): ExternalPerson {
        const copy: ExternalPerson = Object.assign({}, externalPerson);
        return copy;
    }
}
