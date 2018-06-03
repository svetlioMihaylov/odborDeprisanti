import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DocumentTemplates } from './document-templates.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DocumentTemplates>;

@Injectable()
export class DocumentTemplatesService {

    private resourceUrl =  SERVER_API_URL + 'api/document-templates';

    constructor(private http: HttpClient) { }

    create(documentTemplates: DocumentTemplates): Observable<EntityResponseType> {
        const copy = this.convert(documentTemplates);
        return this.http.post<DocumentTemplates>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(documentTemplates: DocumentTemplates): Observable<EntityResponseType> {
        const copy = this.convert(documentTemplates);
        return this.http.put<DocumentTemplates>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DocumentTemplates>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DocumentTemplates[]>> {
        const options = createRequestOption(req);
        return this.http.get<DocumentTemplates[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DocumentTemplates[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DocumentTemplates = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DocumentTemplates[]>): HttpResponse<DocumentTemplates[]> {
        const jsonResponse: DocumentTemplates[] = res.body;
        const body: DocumentTemplates[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DocumentTemplates.
     */
    private convertItemFromServer(documentTemplates: DocumentTemplates): DocumentTemplates {
        const copy: DocumentTemplates = Object.assign({}, documentTemplates);
        return copy;
    }

    /**
     * Convert a DocumentTemplates to a JSON which can be sent to the server.
     */
    private convert(documentTemplates: DocumentTemplates): DocumentTemplates {
        const copy: DocumentTemplates = Object.assign({}, documentTemplates);
        return copy;
    }
}
