import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { EmployeePossition } from './employee-possition.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EmployeePossition>;

@Injectable()
export class EmployeePossitionService {

    private resourceUrl =  SERVER_API_URL + 'api/employee-possitions';

    constructor(private http: HttpClient) { }

    create(employeePossition: EmployeePossition): Observable<EntityResponseType> {
        const copy = this.convert(employeePossition);
        return this.http.post<EmployeePossition>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(employeePossition: EmployeePossition): Observable<EntityResponseType> {
        const copy = this.convert(employeePossition);
        return this.http.put<EmployeePossition>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EmployeePossition>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EmployeePossition[]>> {
        const options = createRequestOption(req);
        return this.http.get<EmployeePossition[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EmployeePossition[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EmployeePossition = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EmployeePossition[]>): HttpResponse<EmployeePossition[]> {
        const jsonResponse: EmployeePossition[] = res.body;
        const body: EmployeePossition[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EmployeePossition.
     */
    private convertItemFromServer(employeePossition: EmployeePossition): EmployeePossition {
        const copy: EmployeePossition = Object.assign({}, employeePossition);
        return copy;
    }

    /**
     * Convert a EmployeePossition to a JSON which can be sent to the server.
     */
    private convert(employeePossition: EmployeePossition): EmployeePossition {
        const copy: EmployeePossition = Object.assign({}, employeePossition);
        return copy;
    }
}
