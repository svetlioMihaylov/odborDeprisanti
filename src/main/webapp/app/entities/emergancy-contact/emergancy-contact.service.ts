import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { EmergancyContact } from './emergancy-contact.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EmergancyContact>;

@Injectable()
export class EmergancyContactService {

    private resourceUrl =  SERVER_API_URL + 'api/emergancy-contacts';

    constructor(private http: HttpClient) { }

    create(emergancyContact: EmergancyContact): Observable<EntityResponseType> {
        const copy = this.convert(emergancyContact);
        return this.http.post<EmergancyContact>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(emergancyContact: EmergancyContact): Observable<EntityResponseType> {
        const copy = this.convert(emergancyContact);
        return this.http.put<EmergancyContact>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EmergancyContact>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EmergancyContact[]>> {
        const options = createRequestOption(req);
        return this.http.get<EmergancyContact[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EmergancyContact[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EmergancyContact = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EmergancyContact[]>): HttpResponse<EmergancyContact[]> {
        const jsonResponse: EmergancyContact[] = res.body;
        const body: EmergancyContact[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EmergancyContact.
     */
    private convertItemFromServer(emergancyContact: EmergancyContact): EmergancyContact {
        const copy: EmergancyContact = Object.assign({}, emergancyContact);
        return copy;
    }

    /**
     * Convert a EmergancyContact to a JSON which can be sent to the server.
     */
    private convert(emergancyContact: EmergancyContact): EmergancyContact {
        const copy: EmergancyContact = Object.assign({}, emergancyContact);
        return copy;
    }
}
