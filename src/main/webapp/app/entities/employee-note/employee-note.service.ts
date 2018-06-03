import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { EmployeeNote } from './employee-note.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EmployeeNote>;

@Injectable()
export class EmployeeNoteService {

    private resourceUrl =  SERVER_API_URL + 'api/employee-notes';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(employeeNote: EmployeeNote): Observable<EntityResponseType> {
        const copy = this.convert(employeeNote);
        return this.http.post<EmployeeNote>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(employeeNote: EmployeeNote): Observable<EntityResponseType> {
        const copy = this.convert(employeeNote);
        return this.http.put<EmployeeNote>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EmployeeNote>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EmployeeNote[]>> {
        const options = createRequestOption(req);
        return this.http.get<EmployeeNote[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EmployeeNote[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EmployeeNote = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EmployeeNote[]>): HttpResponse<EmployeeNote[]> {
        const jsonResponse: EmployeeNote[] = res.body;
        const body: EmployeeNote[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EmployeeNote.
     */
    private convertItemFromServer(employeeNote: EmployeeNote): EmployeeNote {
        const copy: EmployeeNote = Object.assign({}, employeeNote);
        copy.date = this.dateUtils
            .convertLocalDateFromServer(employeeNote.date);
        return copy;
    }

    /**
     * Convert a EmployeeNote to a JSON which can be sent to the server.
     */
    private convert(employeeNote: EmployeeNote): EmployeeNote {
        const copy: EmployeeNote = Object.assign({}, employeeNote);
        copy.date = this.dateUtils
            .convertLocalDateToServer(employeeNote.date);
        return copy;
    }
}
