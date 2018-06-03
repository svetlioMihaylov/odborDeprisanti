import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { WorkDetails } from './work-details.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<WorkDetails>;

@Injectable()
export class WorkDetailsService {

    private resourceUrl =  SERVER_API_URL + 'api/work-details';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(workDetails: WorkDetails): Observable<EntityResponseType> {
        const copy = this.convert(workDetails);
        return this.http.post<WorkDetails>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(workDetails: WorkDetails): Observable<EntityResponseType> {
        const copy = this.convert(workDetails);
        return this.http.put<WorkDetails>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<WorkDetails>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<WorkDetails[]>> {
        const options = createRequestOption(req);
        return this.http.get<WorkDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<WorkDetails[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: WorkDetails = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<WorkDetails[]>): HttpResponse<WorkDetails[]> {
        const jsonResponse: WorkDetails[] = res.body;
        const body: WorkDetails[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to WorkDetails.
     */
    private convertItemFromServer(workDetails: WorkDetails): WorkDetails {
        const copy: WorkDetails = Object.assign({}, workDetails);
        copy.startDate = this.dateUtils
            .convertLocalDateFromServer(workDetails.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateFromServer(workDetails.endDate);
        copy.endOfProbationDate = this.dateUtils
            .convertLocalDateFromServer(workDetails.endOfProbationDate);
        return copy;
    }

    /**
     * Convert a WorkDetails to a JSON which can be sent to the server.
     */
    private convert(workDetails: WorkDetails): WorkDetails {
        const copy: WorkDetails = Object.assign({}, workDetails);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(workDetails.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(workDetails.endDate);
        copy.endOfProbationDate = this.dateUtils
            .convertLocalDateToServer(workDetails.endOfProbationDate);
        return copy;
    }
}
