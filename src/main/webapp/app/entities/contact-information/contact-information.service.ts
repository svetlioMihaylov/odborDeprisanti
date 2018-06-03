import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ContactInformation } from './contact-information.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ContactInformation>;

@Injectable()
export class ContactInformationService {

    private resourceUrl =  SERVER_API_URL + 'api/contact-informations';

    constructor(private http: HttpClient) { }

    create(contactInformation: ContactInformation): Observable<EntityResponseType> {
        const copy = this.convert(contactInformation);
        return this.http.post<ContactInformation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(contactInformation: ContactInformation): Observable<EntityResponseType> {
        const copy = this.convert(contactInformation);
        return this.http.put<ContactInformation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ContactInformation>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ContactInformation[]>> {
        const options = createRequestOption(req);
        return this.http.get<ContactInformation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ContactInformation[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ContactInformation = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ContactInformation[]>): HttpResponse<ContactInformation[]> {
        const jsonResponse: ContactInformation[] = res.body;
        const body: ContactInformation[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ContactInformation.
     */
    private convertItemFromServer(contactInformation: ContactInformation): ContactInformation {
        const copy: ContactInformation = Object.assign({}, contactInformation);
        return copy;
    }

    /**
     * Convert a ContactInformation to a JSON which can be sent to the server.
     */
    private convert(contactInformation: ContactInformation): ContactInformation {
        const copy: ContactInformation = Object.assign({}, contactInformation);
        return copy;
    }
}
