import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { EmployeePhoto } from './employee-photo.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EmployeePhoto>;

@Injectable()
export class EmployeePhotoService {

    private resourceUrl =  SERVER_API_URL + 'api/employee-photos';

    constructor(private http: HttpClient) { }

    create(employeePhoto: EmployeePhoto): Observable<EntityResponseType> {
        const copy = this.convert(employeePhoto);
        return this.http.post<EmployeePhoto>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(employeePhoto: EmployeePhoto): Observable<EntityResponseType> {
        const copy = this.convert(employeePhoto);
        return this.http.put<EmployeePhoto>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EmployeePhoto>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EmployeePhoto[]>> {
        const options = createRequestOption(req);
        return this.http.get<EmployeePhoto[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EmployeePhoto[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EmployeePhoto = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EmployeePhoto[]>): HttpResponse<EmployeePhoto[]> {
        const jsonResponse: EmployeePhoto[] = res.body;
        const body: EmployeePhoto[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EmployeePhoto.
     */
    private convertItemFromServer(employeePhoto: EmployeePhoto): EmployeePhoto {
        const copy: EmployeePhoto = Object.assign({}, employeePhoto);
        return copy;
    }

    /**
     * Convert a EmployeePhoto to a JSON which can be sent to the server.
     */
    private convert(employeePhoto: EmployeePhoto): EmployeePhoto {
        const copy: EmployeePhoto = Object.assign({}, employeePhoto);
        return copy;
    }
}
