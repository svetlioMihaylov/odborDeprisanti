import { BaseEntity } from './../../shared';

export class VacationRequests implements BaseEntity {
    constructor(
        public id?: number,
        public startDate?: string,
        public endDate?: string,
        public duration?: number,
        public isApproved?: boolean,
        public isCompleated?: boolean,
        public owner?: BaseEntity,
    ) {
        this.isApproved = false;
        this.isCompleated = false;
    }
}
